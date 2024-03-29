<?php // $Id: export.php 12557 2010-08-30 10:11:32Z zefredz $
// vim: expandtab sw=4 ts=4 sts=4:

/**
 * Script handling the download of assignments
 * As from 1.9.6 replaces $cmd = 'exDownload' in both work.php and work_list.php
 * As from 1.9.6 uses pclzip instead of zip.lib
 *
 * @version     1.9 $Revision: 12557 $
 * @copyright   2001-2010 Universite catholique de Louvain (UCL)
 * @author      FUNDP - WebCampus <webcampus@fundp.ac.be>
 * @author      Jean-Roch Meurisse <jmeuriss@fundp.ac.be>
 * @license     http://www.gnu.org/copyleft/gpl.html
 *              GNU GENERAL PUBLIC LICENSE version 2 or later
 * @package     CLWORK
 * @since       Claroline 1.9.6
 */

$tlabelReq = 'CLWRK';

//load Claroline kernel
require_once dirname( __FILE__ ) . '/../../claroline/inc/claro_init_global.inc.php';

if ( !claro_is_in_a_course() || !claro_is_course_allowed() ) claro_disp_auth_form( true );

if( !claro_is_allowed_to_edit() )
{
  claro_die( get_lang( 'Not allowed' ) );
}

//load required libs
require_once get_module_path( $tlabelReq ) . '/lib/assignment.class.php';
require_once get_path( 'incRepositorySys' ) . '/lib/fileManage.lib.php';
require_once get_path( 'incRepositorySys' ) . '/lib/thirdparty/pclzip/pclzip.lib.php';

//init general purpose vars
$is_allowedToEdit = claro_is_allowed_to_edit();
$dialogBox = new DialogBox();

$downloadMode = isset( $_REQUEST['downloadMode'] ) && is_string( $_REQUEST['downloadMode'] ) ? $_REQUEST['downloadMode'] : 'all';
$assignmentId = isset( $_REQUEST['assigId'] ) && is_numeric( $_REQUEST['assigId'] ) ? $_REQUEST['assigId'] : 0;

if( $assignmentId )
{
    $assignment = new Assignment();
    $assignment->load( $assignmentId );
}

if( get_conf( 'allow_download_all_submissions' ) ) 
{
    $courseTbl = claro_sql_get_course_tbl();
    $submissionTbl = $courseTbl['wrk_submission'];
    
    if( $downloadMode == 'from' )
    {
        if( isset($_REQUEST['hour']) && is_numeric($_REQUEST['hour']) )       $hour = (int) $_REQUEST['hour'];
        else                                                                  $hour = 0;
        if( isset($_REQUEST['minute']) && is_numeric($_REQUEST['minute']) ) $minute = (int) $_REQUEST['minute'];
        else                                                                  $minute = 0;

        if( isset($_REQUEST['month']) && is_numeric($_REQUEST['month']) )   $month = (int) $_REQUEST['month'];
        else                                                                  $month = 0;
        if( isset($_REQUEST['day']) && is_numeric($_REQUEST['day']) )       $day = (int) $_REQUEST['day'];
        else                                                                  $day = 0;
        if( isset($_REQUEST['year']) && is_numeric($_REQUEST['year']) )       $year = (int) $_REQUEST['year'];
        else                                                                  $year = 0;

        $unixRequestDate = mktime( $hour, $minute, '00', $month, $day, $year );

        if( $unixRequestDate >= time() )
        {
            $dialogBox->error( get_lang( 'Warning : chosen date is in the future' ) );
        }

        $downloadRequestDate = date( 'Y-m-d G:i:s', $unixRequestDate );
        
        

        $wanted = '_' . replace_dangerous_char( get_lang( 'From' ) ) . '_' . date( 'Y_m_d', $unixRequestDate ) . '_'
                . replace_dangerous_char( get_lang( 'to' ) ) . '_' . date( 'Y_m_d' )
        ;
        $sqlDateCondition = " AND `last_edit_date` >= '" . $downloadRequestDate . "' ";
    }
    else
    {
        $wanted = '';

        $sqlDateCondition = '';
    }
    
    if( $assignmentId == 0 )
    {
        $assignmentRestriction = '';
        $zipPath = get_path( 'coursesRepositorySys' ) . claro_get_current_course_id() . '/work/tmp';
        $zipName = claro_get_current_course_id() . '_' . replace_dangerous_char( get_lang( 'Assignments' ) ) . $wanted . '.zip';      
    }
    else
    {
        $assignmentRestriction = " AND `assignment_id` = " . (int)$assignmentId;
        $zipPath = get_path( 'coursesRepositorySys' ) . claro_get_current_course_id() . '/work/assig_' . (int)$assignmentId . '/' . 'tmp';
        $zipName = claro_get_current_course_id() . '_' . replace_dangerous_char( $assignment->getTitle(), 'strict' ) . $wanted . '.zip';
    }
    
    $downloadArchiveFolderPath = get_path('coursesRepositorySys') . claro_get_course_path() . '/tmp/zip';
    
    if ( !is_dir( $downloadArchiveFilePath ) )
    {
        mkdir( $downloadArchiveFilePath, CLARO_FILE_PERMISSIONS, true );
    }
    
    $downloadArchiveFilePath = $downloadArchiveFolderPath . '/' . $zipName;

    $sql = "SELECT `id`, 
                   `assignment_id`,
                   `authors`,
                   `submitted_text`,
                   `submitted_doc_path`,
                   `title`,
                   `creation_date`,
                   `last_edit_date`
              FROM `" . $submissionTbl . "`
             WHERE `parent_id` IS NULL "
                   . $assignmentRestriction 
                   . $sqlDateCondition . "
          ORDER BY `authors`, 
                   `creation_date`";

    if( !is_dir( $zipPath ) )
    {
        mkdir( $zipPath, CLARO_FILE_PERMISSIONS, true );
    }
    
    $results = claro_sql_query_fetch_all( $sql );

    if( is_array( $results ) && !empty( $results ) )
    {
        $previousAuthors = '';
        $i = 1;

        foreach ( $results as $row => $result )
        {
            //create assignment directory if necessary
            if( $assignmentId == 0 )
            {
                if( !is_dir( $zipPath . '/' . get_lang( 'Assignment' ) . '_' . $result['assignment_id'] . '/' ) )
                {
                    mkdir( $zipPath . '/' . get_lang( 'Assignment' ) . '_' . $result['assignment_id'] . '/', CLARO_FILE_PERMISSIONS, true );
                }
                
                $assigDir = '/' . get_lang( 'Assignment' ) . '_' . $result['assignment_id'] . '/';
            }
            else
            {
                $assigDir = '';
            }
            
            $assignmentPath = get_path( 'coursesRepositorySys' ) . claro_get_current_course_id() . '/work/assig_' . (int)$result['assignment_id'] . '/';
            
            //  count author's submissions for the name of directory
            if( $result['authors'] != $previousAuthors )
            {
                $i = 1;
                $previousAuthors = $result['authors'];
            }
            else
            {
                $i++;
            }

            $authorsDir = replace_dangerous_char( $result['authors'] ) . '/';
            
            if( !is_dir( $zipPath . $assigDir . '/' . $authorsDir ) )
            {
                mkdir( $zipPath . $assigDir . '/' . $authorsDir, CLARO_FILE_PERMISSIONS, true );
            }
            
            $submissionPrefix = $assigDir . $authorsDir . replace_dangerous_char( get_lang( 'Submission' ) ) . '_' . $i . '_';

            // attached file
            if( !empty( $result['submitted_doc_path'] ) )
            {
                if( file_exists( $assignmentPath . $result['submitted_doc_path'] ) )
                {
                    copy( $assignmentPath . $result['submitted_doc_path'], $zipPath . '/' . $submissionPrefix . $result['submitted_doc_path'] );
                }
            }

            // description file
            $txtFileName = replace_dangerous_char( get_lang( 'Descriptions' ) ) . '.html';

            $htmlContent = '<html><head></head><body>' . "\n"
            .     get_lang( 'Title' ) . ' : ' . $result['title'] . '<br />' . "\n"
            .     get_lang( 'First submission date' ) . ' : ' . $result['creation_date']. '<br />' . "\n"
            .     get_lang( 'Last edit date' ) . ' : ' . $result['last_edit_date'] . '<br />' . "\n"
            ;

            if( !empty( $result['submitted_doc_path'] ) )
            {
                $htmlContent .= get_lang( 'Attached file' ) . ' : ' . $submissionPrefix . $result['submitted_doc_path']. '<br />' . "\n";
            }

            $htmlContent .= '<div>' . "\n"
            .     '<h3>' . get_lang( 'Description' ) . '</h3>' . "\n"
            .     $result['submitted_text']
            .     '</div>' . "\n"
            .     '</body></html>';
            
            file_put_contents( $zipPath . '/' . $submissionPrefix . $txtFileName, $htmlContent );
        }
        
        $zipFile = new PclZip( $downloadArchiveFilePath );
        $created = $zipFile->create( $zipPath, PCLZIP_OPT_REMOVE_PATH, $zipPath );
        
        if ( !$created ) 
        {
            $dialogBox->error( get_lang( 'Unable to create the archive' ) );
        } 
        else
        {
            claro_delete_file( $zipPath );
            
            header( 'Content-Description: File Transfer' );
            header( 'Content-Type: application/force-download' );
            header( 'Content-Length: ' . filesize( $downloadArchiveFilePath ) );
            header( 'Content-Disposition: attachment; filename=' . $zipName );
            
            readfile( $downloadArchiveFilePath );
            
            claro_delete_file( $downloadArchiveFilePath );
            
            exit();
        }
    }
    else
    {
        $dialogBox->error( get_lang( 'There is no submission available for download with these settings.' ) );
    }
}

$out .= $dialogBox->render();

ClaroBreadCrumbs::getInstance()->prepend( get_lang( 'Work' ), 'work.php' );

$claroline->display->body->appendContent($out);

echo $claroline->display->render();