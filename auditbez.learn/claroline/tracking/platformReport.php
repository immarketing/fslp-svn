<?php // $Id: platformReport.php 11781 2009-05-25 07:30:17Z zefredz $
/**
 * CLAROLINE
 *
 * @version 1.9 $Revision: 11781 $
 *
 * @copyright (c) 2001-2009 Universite catholique de Louvain (UCL)
 *
 * @author Sebastien Piraux <seb@claroline.net>
 *
 * @package CLTRACK
 */

/*
 * Kernel
 */
require_once dirname( __FILE__ ) . '/../../claroline/inc/claro_init_global.inc.php';



/*
 * Permissions
 */
if( ! get_conf('is_trackingEnabled') ) claro_die(get_lang('Tracking has been disabled by system administrator.'));

if( ! claro_is_user_authenticated() ) claro_disp_auth_form();
if( ! claro_is_platform_admin() ) claro_die( get_lang('Not allowed') );

/*
 * Libraries
 */
uses( 'user.lib', 'courselist.lib' );

// todo move this lib in tracking/lib
require_once get_path('incRepositorySys') . '/lib/statsUtils.lib.inc.php';


/*
 * DB tables definition
 */
$tbl_mdb_names       = claro_sql_get_main_tbl();
$tbl_course          = $tbl_mdb_names['course'           ];
$tbl_rel_course_user = $tbl_mdb_names['rel_course_user'  ];
$tbl_user            = $tbl_mdb_names['user'             ];
$tbl_tracking_event  = $tbl_mdb_names['tracking_event'];



/*
 * Output
 */
$cssLoader = CssLoader::getInstance();
$cssLoader->load( 'tracking', 'screen');


ClaroBreadCrumbs::getInstance()->prepend( get_lang('Administration'), get_path('rootAdminWeb') );
$nameTools = get_lang('Platform statistics');

$html = '';    

$html .= claro_html_tool_title( $nameTools );


/*
 * Platform access and logins
 */

$header = get_lang('Access');

$content = '<ul>';

//--  all
$sql = "SELECT count(*)
          FROM `".$tbl_tracking_event."`
         WHERE `type` = 'platform_access'";

$count = claro_sql_query_get_single_value($sql);
$content .= '<li>'.get_lang('Total').' : '.$count.'</li>'."\n";

//--  last 31 days
$sql = "SELECT count(*)
          FROM `" . $tbl_tracking_event . "`
         WHERE `type` = 'platform_access'
           AND (`date` > DATE_ADD(CURDATE(), INTERVAL -31 DAY))";

$count = claro_sql_query_get_single_value($sql);
$content .= '<li>'.get_lang('Last 31 days').' : '.$count.'</li>'."\n";

//--  last 7 days
$sql = "SELECT count(*)
          FROM `".$tbl_tracking_event."`
         WHERE `type` = 'platform_access'
           AND (`date` > DATE_ADD(CURDATE(), INTERVAL -7 DAY))";

$count = claro_sql_query_get_single_value($sql);
$content .= '<li>'.get_lang('Last 7 days').' : '.$count.'</li>'."\n";

//--  yesterday
$sql = "SELECT count(*)
          FROM `".$tbl_tracking_event."`
         WHERE `type` = 'platform_access'
           AND (`date` > DATE_ADD(CURDATE(), INTERVAL -1 DAY))
           AND (`date` < CURDATE() )";

$count = claro_sql_query_get_single_value($sql);
$content .= '<li>'.get_lang('Yesterday').' : '.$count.'</li>'."\n";

//--  today
$sql = "SELECT count(*)
          FROM `".$tbl_tracking_event."`
         WHERE `type` = 'platform_access'
           AND (`date` > CURDATE() )";

$count = claro_sql_query_get_single_value($sql);
$content .= '<li>'.get_lang('This day').' : '.$count.'</li>'."\n";

$content .= '</ul>' . "\n";

$footer = '<a href="platform_access_details.php">'.get_lang('Traffic Details').'</a>';

$html .= renderStatBlock( $header, $content, $footer);

//----------------------------  logins
$header = get_lang('Logins');

$content = '<ul>';

//--  all
$sql = "SELECT count(*)
          FROM `".$tbl_tracking_event."`
         WHERE `type` = 'user_login'";

$count = claro_sql_query_get_single_value($sql);
$content .= '<li>'.get_lang('Total').' : '.$count.'</li>'."\n";

//--  last 31 days
$sql = "SELECT count(*)
          FROM `".$tbl_tracking_event."`
         WHERE `type` = 'user_login'
           AND (`date` > DATE_ADD(CURDATE(), INTERVAL -31 DAY))";

$count = claro_sql_query_get_single_value($sql);
$content .= '<li>'.get_lang('Last 31 days').' : '.$count.'</li>'."\n";

//--  last 7 days
$sql = "SELECT count(*)
          FROM `".$tbl_tracking_event."`
         WHERE `type` = 'user_login'
           AND (`date` > DATE_ADD(CURDATE(), INTERVAL -7 DAY))";

$count = claro_sql_query_get_single_value($sql);
$content .= '<li>'.get_lang('Last 7 days').' : '.$count.'</li>'."\n";

//--  yesterday
$sql = "SELECT count(*)
          FROM `".$tbl_tracking_event."`
         WHERE `type` = 'user_login'
           AND (`date` > DATE_ADD(CURDATE(), INTERVAL -1 DAY))
           AND (`date` < CURDATE() )";

$count = claro_sql_query_get_single_value($sql);
$content .= '<li>'.get_lang('Yesterday').' : '.$count.'</li>'."\n";

//--  today
$sql = "SELECT count(*)
          FROM `".$tbl_tracking_event."`
         WHERE `type` = 'user_login'
           AND (`date` > CURDATE() )";

$count = claro_sql_query_get_single_value($sql);
$content .= '<li>'.get_lang('This day').' : '.$count.'</li>'."\n";

$content .= '</ul>' . "\n";

$footer = '';

$html .= renderStatBlock( $header, $content, $footer);

    /***************************************************************************
     *
     *        Main
     *
     ***************************************************************************/

$header = get_lang('Courses');

$content = '';

//--  number of courses
$sql = "SELECT count(*)
          FROM `" . $tbl_course . "`";
$count = claro_sql_query_get_single_value($sql);
$content .= '&nbsp;&nbsp;&nbsp;' . get_lang('Number of courses') . ' : ' . $count.'<br />'."\n";

//--  number of courses by faculte
$sql = "SELECT `faculte`, count( * ) AS `nbr`
          FROM `" . $tbl_course . "`
         WHERE `faculte` IS NOT NULL
         GROUP BY `faculte`";

$content .= buildTab2Col($sql, get_lang('Number of courses by faculty'));

//--  number of courses by language
$sql = "SELECT `language`, count( * ) AS `nbr`
          FROM `" . $tbl_course . "`
         WHERE `language` IS NOT NULL
         GROUP BY `language`";

$content .= buildTab2Col($sql,get_lang('Number of courses by language'));

//--  number of courses by access
$sql = "SELECT `access`, count( * ) AS `nbr`
            FROM `" . $tbl_course . "`
            WHERE `access` IS NOT NULL
            GROUP BY `access`";

$content .= buildTab2Col($sql, get_lang('Number of courses by access'));

//--  number of courses by registration
$sql = "SELECT `registration`, count( * ) AS `nbr`
            FROM `" . $tbl_course . "`
            WHERE `registration` IS NOT NULL
            GROUP BY `registration`";

$content .= buildTab2Col($sql, get_lang('Number of courses by enrollment'));

//--  number of courses by visibility
$sql = "SELECT `visibility`, count( * ) AS `nbr`
            FROM `" . $tbl_course . "`
            WHERE `visibility` IS NOT NULL
            GROUP BY `visibility`";

$content .= buildTab2Col($sql, get_lang('Number of courses by visibility'));

$footer = '';

$html .= renderStatBlock( $header, $content, $footer);

//-- USERS
$header = get_lang('Users');

$content = '';
//--  total number of users
$sql = "SELECT count(*)
            FROM `".$tbl_user."`";
$count = claro_sql_query_get_single_value($sql);
$content .= '&nbsp;&nbsp;&nbsp;'.get_lang('Number of users').' : '.$count.'<br />'."\n";

//--  number of users by course
$sql = "SELECT C.`code`, count( CU.user_id ) as `nb`
            FROM `" . $tbl_course . "` C, `" . $tbl_rel_course_user . "` CU
            WHERE CU.`code_cours` = C.`code`
                AND `code` IS NOT NULL
            GROUP BY C.`code`
            ORDER BY nb DESC";

$content .= buildTab2Col($sql, get_lang('Number of users by course'));

//--  number of users by faculte
$sql = "SELECT C.`faculte`, count( CU.`user_id` ) AS `nbr`
            FROM `" . $tbl_course . "` C, `" . $tbl_rel_course_user . "` CU
            WHERE CU.`code_cours` = C.`code`
                AND C.`faculte` IS NOT NULL
            GROUP BY C.`faculte`";

$content .= buildTab2Col($sql, get_lang('Number of users by faculty'));

//--  number of users by status
$sql = "SELECT `isCourseCreator`, count( `user_id` ) AS `nbr`
            FROM `".$tbl_user."`
            WHERE `isCourseCreator` IS NOT NULL
            GROUP BY `isCourseCreator`";

$content .= buildTab2Col($sql, get_lang('Number of users by status'));

$footer = '';

$html .= renderStatBlock( $header, $content, $footer);
 

/*
 * Access to tools
 * // due to the moving of access tables in course DB this part of the code exec (nbCourser+1) queries
 * // this can create heavy overload on servers ... should be reconsidered
 *
 */
/*
$header = get_lang('`Tools');

$content = '';

// display list of course of the student with links to the corresponding userLog
$sql = "SELECT code, dbName
      FROM    `" . $tbl_course . "`
      ORDER BY code ASC";

$resCourseList = claro_sql_query_fetch_all($sql);
$resultsTools=array();
foreach ( $resCourseList as $course )
{
    // TODO use a archive page that get's stats of all course and resume everything in a single table
    // TODO : use claro_sql_get_course_tbl_name
    $TABLEACCESSCOURSE = get_conf('courseTablePrefix') . $course['dbName'] . get_conf('dbGlu') . "track_e_access";
    $sql = "SELECT count( `access_id` ) AS nb, `access_tlabel`
            FROM `".$TABLEACCESSCOURSE."`
            WHERE `access_tid` IS NOT NULL
            GROUP BY `access_tid`";

    $access = claro_sql_query_fetch_all($sql);

    // look for each tool of the course in re
    foreach( $access as $count )
    {
        if ( !isset($resultsTools[$count['access_tlabel']]) )
        {
            $resultsTools[$count['access_tlabel']] = $count['nb'];
        }
        else
        {
            $resultsTools[$count['access_tlabel']] += $count['nb'];
        }
    }
}

$content .= '<table cellpadding="2" cellspacing="1" class="claroTable" align="center">'
 . '<thead>'
 . '<tr class="headerX">'."\n"
 . '<th>&nbsp;'.get_lang('Name of the tool').'</th>'."\n"
 . '<th>&nbsp;'.get_lang('Total Clicks').'</th>'."\n"
 . '</tr>'
 . '</thead>'."\n"
 . '<tbody>'."\n"
 ;

if (is_array($resultsTools))
{
  arsort($resultsTools);
  foreach( $resultsTools as $tool => $nbr)
  {
      $content .= '<tr>' . "\n"
         . '<td>' . $toolNameList[$tool].'</td>'."\n"
         . '<td>' . $nbr.'</td>'."\n"
         . '</tr>' . "\n\n"
         ;
  }
}
else
{
  $content .= '<tr>'."\n"
     . '<td colspan="2"><center>'.get_lang('No result').'</center></td>'."\n"
     . '</tr>'."\n"
     ;
}

$content .= '</tbody>'."\n"
 . '</table>'."\n\n"
 ;

$footer = '';

$html .= renderStatBlock( $header, $content, $footer);

*/

/*
 * Output rendering
 */
$claroline->display->body->setContent($html);

echo $claroline->display->render();
?>