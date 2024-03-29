<?php //$Id: admin_class_cours.php 11787 2009-05-26 13:05:09Z dimitrirambout $
/**
 * CLAROLINE
 *
 * this tool manage the
 *
 * @version 1.9
 *
 * @copyright (c) 2001-2005 Universite catholique de Louvain (UCL)
 *
 * @license http://www.gnu.org/copyleft/gpl.html (GPL) GENERAL PUBLIC LICENSE
 *
 * @author Damien Garros <dgarros@univ-catholyon.fr>
 */
$userPerPage = 20; // numbers of cours to display on the same page

// initialisation of global variables and used libraries
require '../inc/claro_init_global.inc.php';

require_once get_path('incRepositorySys') . '/lib/pager.lib.php';
require_once get_path('incRepositorySys') . '/lib/class.lib.php';
require_once get_path('incRepositorySys') . '/lib/user.lib.php';
require_once get_path('incRepositorySys') . '/lib/admin.lib.inc.php';

// Security check
if ( ! claro_is_user_authenticated() ) claro_disp_auth_form();
if ( ! claro_is_platform_admin() ) claro_die(get_lang('Not allowed'));

/**#@+
 * DB tables definition
 * @var $tbl_mdb_names array table name for the central database
 */

$tbl_mdb_names = claro_sql_get_main_tbl();
$tbl_cours               = $tbl_mdb_names['course'];
$tbl_course_class          = $tbl_mdb_names['rel_course_class'];
$tbl_class              = $tbl_mdb_names['class'];

// javascript confirm pop up declaration

$htmlHeadXtra[] =
         "<script>
         function confirmationUnReg (name)
         {
             if (confirm(\"".clean_str_for_javascript(get_lang('Are you sure you want to unregister'))."\"+ name + \"? \"))
                 {return true;}
             else
                 {return false;}
         }
         </script>";

//------------------------------------
// Execute COMMAND section
//------------------------------------

$cmd = isset($_REQUEST['cmd'])?$_REQUEST['cmd']:null;
$class_id = isset($_REQUEST['class_id'])?(int)$_REQUEST['class_id']:0;
$course_id = isset($_REQUEST['course_id'])?$_REQUEST['course_id']:null;

// find info about the class

if ( ($classinfo = class_get_properties ($class_id)) === false )
{
    $class_id = 0;
}

if ( !empty($class_id) )
{
    switch ($cmd)
    {
        case 'unsubscribe' :
            unregister_class_to_course($class_id,$course_id);
            break;

        default :
            // No command
    }

    //find this class current content
    // TODO Factorise this statement
    $sql = "SELECT distinct (CC.`courseId`), C.`code`, C.`language` ,C.`intitule`,C.`faculte`,C.`titulaires`
            FROM `".$tbl_course_class."` CC, `".$tbl_cours."` C
            WHERE C.`code` = CC.`courseId`
            AND CC.`classId` = '". $class_id ."'";

    // deal with session variables for search criteria

    if (isset($_REQUEST['dir'])) {$_SESSION['admin_user_class_dir']  = ($_REQUEST['dir']=='DESC'?'DESC':'ASC');}

    // first see is direction must be changed

    if (isset($_REQUEST['chdir']) && ($_REQUEST['chdir']=="yes"))
    {
        if     ($_SESSION['admin_course_class_dir'] == 'ASC')  {$_SESSION['admin_course_class_dir']='DESC';}
        elseif ($_SESSION['admin_course_class_dir'] == 'DESC') {$_SESSION['admin_course_class_dir']='ASC';}
    }
    elseif (!isset($_SESSION['admin_course_class_dir']))
    {
        $_SESSION['admin_course_class_dir'] = 'DESC';
    }

    // deal with REORDER

    if (isset($_REQUEST['order_crit']))
    {
        $_SESSION['admin_course_class_order_crit'] = $_REQUEST['order_crit'];
        if ($_REQUEST['order_crit']=='user_id')
        {
            $_SESSION['admin_course_class_order_crit'] = 'U`.`user_id';
        }
    }

    if (isset($_SESSION['admin_course_class_order_crit']))
    {
        $toAdd = " ORDER BY `".$_SESSION['admin_course_class_order_crit'] . "` " . $_SESSION['admin_course_class_dir'];
        $sql.=$toAdd;
    }

    //Build pager with SQL request
    if (!isset($_REQUEST['offset'])) $offset = "0";
    else                             $offset = $_REQUEST['offset'];

    $myPager = new claro_sql_pager($sql, $offset, $userPerPage);
    $resultList = $myPager->get_result_list();

}

//------------------------------------
// DISPLAY
//------------------------------------

$dialogBox = new DialogBox();

// Deal with interbredcrumps
ClaroBreadCrumbs::getInstance()->prepend( get_lang('Classes'), get_path('rootAdminWeb'). 'admin_class.php' );
ClaroBreadCrumbs::getInstance()->prepend( get_lang('Administration'), get_path('rootAdminWeb') );
$nameTools = get_lang('Class members');

$out = '';

if ( empty($class_id) )
{
    $dialogBox->error( get_lang('Class not found') );
    $out .= $dialogBox->render();
}
else
{
    // Display tool title

    $out .= claro_html_tool_title(get_lang('Course list') . ' : ' . $classinfo['name']);

    // TOOL LINKS
    $cmd_menu[] = '<a class="claroCmd" href="' . get_path('clarolineRepositoryWeb').'auth/courses.php?cmd=rqReg&amp;fromAdmin=class&amp;class_id='.$class_id.'"><img src="' . get_icon_url('enroll') . '" /> ' . get_lang('Register class for course') . '</a>';
    $out .= '<p>' . claro_html_menu_horizontal( $cmd_menu ) . '</p>';

    // Pager

    $myPager->disp_pager_tool_bar($_SERVER['PHP_SELF'].'&amp;class_id='.$class_id);

    // Display list of cours

    // start table...
    // TODO datagrid
    $out .= '<table class="claroTable emphaseLine" width="100%" border="0" cellspacing="2">'
    .    '<thead>'
    .    '<tr class="headerX" align="center" valign="top">'
    .    '<th><a href="' . $_SERVER['PHP_SELF'] . '?class_id='.$class_id.'&amp;order_crit=code&amp;chdir=yes">' . get_lang('Course code') . '</a></th>'
    .    '<th><a href="' . $_SERVER['PHP_SELF'] . '?class_id='.$class_id.'&amp;order_crit=intitule&amp;chdir=yes">' . get_lang('Course title') . '</a></th>'
    .    '<th><a href="' . $_SERVER['PHP_SELF'] . '?class_id='.$class_id.'&amp;order_crit=faculte&amp;chdir=yes">' . get_lang('Category') . '</a></th>'
    .     '<th>' . get_lang('Course settings') . '</th>'
    .    '<th>' . get_lang('Unregister from class') . '</th>'
    .    '</tr>'
    .    '</thead>'
    .    '<tbody>'
    ;

    // Start the list of users...

    foreach($resultList as $list)
    {
        $list['officialCode'] = (isset($list['officialCode']) ? $list['officialCode'] :' - ');

        $out .= '<tr>'
        .    '<td align="center" >' . $list['code']      . '</td>'
        .    '<td align="left" >'   . $list['intitule']          . '</td>'
        .    '<td align="left" >'   . $list['faculte']       . '</td>'
        .     '<td align="center">'
        .    '<a href="../course/settings.php?adminContext=1'
        // TODO cfrom=xxx is probably a hack
        .    '&amp;cidReq=' . $list['code'] . '&amp;cfrom=xxx">'
        .    '<img src="' . get_icon_url('settings') . '" alt="' . get_lang('Course settings') . '" />'
        .    '</a>'
        .    '</td>'
        .    '<td align="center">'
        .    '<a href="'.$_SERVER['PHP_SELF']
        .    '?cmd=unsubscribe&amp;class_id='.$class_id.'&amp;offset='.$offset.'&amp;course_id='.$list['code'].'" '
        .    ' onclick="return confirmationUnReg(\''.clean_str_for_javascript($list['code']).'\');">'
        .    '<img src="' . get_icon_url('unenroll') . '" alt="" />'
        .    '</a>'
        .    '</td>'
        .    '</tr>';
    }

    // end display users table
    if ( empty($resultList) )
    {
        $out .= '<tr>'
        .    '<td colspan="5" align="center">'
        .    get_lang('No course to display')
        .    '<br />'
        .    '<a href="' . get_path('clarolineRepositoryWeb') . 'admin/admin_class.php">'
        .    get_lang('Back')
        .    '</a>'
        .    '</td>'
        .    '</tr>'
        ;
    }
    $out .= '</tbody>' . "\n"
    .    '</table>' . "\n"
    ;

    //Pager

    $myPager->disp_pager_tool_bar($_SERVER['PHP_SELF'].'&amp;class_id='.$class_id);

}

$claroline->display->body->appendContent($out);

echo $claroline->display->render();

?>