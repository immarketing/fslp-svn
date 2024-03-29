<?php // $Id: notify.lib.php 10674 2008-07-16 07:31:24Z zefredz $

// vim: expandtab sw=4 ts=4 sts=4:

/**
 * Claroline notification system
 *
 * @version     1.9 $Revision: 10674 $
 * @copyright   2001-2008 Universite catholique de Louvain (UCL)
 * @author      Claroline Team <info@claroline.net>
 * @author      Frederic Minne <zefredz@claroline.net>
 * @license     http://www.gnu.org/copyleft/gpl.html
 *              GNU GENERAL PUBLIC LICENSE version 2 or later
 * @package     KERNEL
 */

if ( count( get_included_files() ) == 1 )
{
    die( 'The file ' . basename(__FILE__) . ' cannot be accessed directly, use include instead' );
}

FromKernel::uses ( 'core/event.lib' );

function load_current_module_listeners()
{
    $claroline = Claroline::getInstance();

    $path = get_module_path( Claroline::getInstance()->currentModuleLabel() )
        . '/connector/eventlistener.cnr.php';

    if ( file_exists( $path ) )
    {
        if ( claro_debug_mode() )
        {
            pushClaroMessage( 'Load listeners for : ' . Claroline::getInstance()->currentModuleLabel(), 'debug' );
        }

        include $path;
    }
    else
    {
        if ( claro_debug_mode() )
        {
            pushClaroMessage( 'No listeners for : ' . Claroline::getInstance()->currentModuleLabel(), 'warning' );
        }
    }
}

class ClaroNotifier extends EventGenerator
{
    private static $instance = false;

    private function __construct()
    {
    }

    public static function getInstance()
    {
        if  ( ! ClaroNotifier::$instance )
        {
            ClaroNotifier::$instance = new ClaroNotifier;
        }

        return ClaroNotifier::$instance;
    }

    public function notifyCourseEvent($eventType, $cid, $tid, $rid, $gid, $uid)
    {
        $eventArgs = array();
        $eventArgs['cid'] = $cid;
        $eventArgs['tid'] = $tid;
        $eventArgs['rid'] = $rid;
        $eventArgs['gid'] = $gid;
        $eventArgs['uid'] = $uid;
        $eventArgs['date'] = date("Y-m-d H:i:00");

        $this->notifyEvent($eventType, $eventArgs);
    }

    public function event( $type, $args = null)
    {
        if( !is_array($args) )
        {
            $args = array();
        }

        if ( !array_key_exists( 'cid', $args ) && claro_is_in_a_course() )
        {
            $args['cid'] = claro_get_current_course_id();
        }

        if ( !array_key_exists( 'gid', $args ) &&  claro_is_in_a_group() )
        {
            $args['gid'] = claro_get_current_group_id();
        }

        if ( !array_key_exists( 'tid', $args ) && claro_is_in_a_tool() )
        {
            $args['tid'] = claro_get_current_tool_id();
            // $args['tlabel'] = get_current_module_label();
        }

        if ( !array_key_exists( 'uid', $args ) && claro_is_user_authenticated() )
        {
            $args['uid'] = claro_get_current_user_id();
        }

        if ( ! array_key_exists( 'date', $args ) )
        {
            $args['date'] = claro_date("Y-m-d H:i:00");
        }

        $this->notifyEvent( $type, $args );
    }
}

class ClaroNotification extends EventDriven
{
    private static $instance = false;

    private function __construct()
    {
    }

    public static function getInstance()
    {
        if  ( ! ClaroNotification::$instance )
        {
            ClaroNotification::$instance = new ClaroNotification;
        }

        return ClaroNotification::$instance;
    }

    public function trackInCourse( $event )
    {
        $event_args = $event->getArgs();

        $tid        = array_key_exists('tid', $event_args) ? $event_args['tid'] : null;
        $gid        = array_key_exists('gid', $event_args) ? $event_args['gid'] : null;
        $uid        = array_key_exists('uid', $event_args) ? $event_args['uid'] : null;
        $date         = array_key_exists('date', $event_args )  ? $event_args['date']: claro_date("Y-m-d H:i:s");

        if( array_key_exists('data', $event_args) )
        {
            $data = serialize( $event_args['data'] );
        }
        else
        {
            $data = '';
        }

        $eventType  = $event->getEventType();

        if ( claro_debug_mode() )
        {
            Console::message( 'Data added in course tracking '
                    . $eventType . ' : '
                    . var_export( $event, true ) );
        }

        if( claro_is_in_a_course() )
        {
            $tbl_cdb_names = claro_sql_get_course_tbl( claro_get_course_db_name_glued(claro_get_current_course_id()) );
            $tbl_tracking_event  = $tbl_cdb_names['tracking_event'];

            $sql = "INSERT INTO `" . $tbl_tracking_event . "`
                    SET `tool_id` = ". ( is_null($tid) ? "NULL" : "'" . claro_sql_escape($tid) . "'" ). ",
                        `group_id` = ". ( is_null($gid) ? "NULL" : "'" . claro_sql_escape($gid) . "'" ). ",
                        `user_id` = ". ( is_null($uid) ? "NULL" : "'" . claro_sql_escape($uid) . "'" ). ",
                        `date` = '" . $date . "',
                        `type` = '" . claro_sql_escape($eventType) . "',
                        `data` = '" . claro_sql_escape($data) . "'";

            return claro_sql_query($sql);
        }
        else
        {
            return false;
        }

    }

    public function trackInPlatform( $event )
    {
        $event_args = $event->getArgs();

        $cid        = array_key_exists('cid', $event_args) ? $event_args['cid'] : null;
        $tid        = array_key_exists('tid', $event_args) ? $event_args['tid'] : null;
        $uid        = array_key_exists('uid', $event_args) ? $event_args['uid'] : null;
        $date         = array_key_exists('date', $event_args )  ? $event_args['date']: claro_date("Y-m-d H:i:s");

        if( array_key_exists('data', $event_args) )
        {
            $data = serialize( $event_args['data'] );
        }
        else
        {
            $data = '';
        }

        $eventType  = $event->getEventType();

        if ( claro_debug_mode() )
        {
            Console::message( 'Data added in platform tracking '
                    . $eventType . ' : '
                    . var_export( $event, true ) );
        }

        $tbl_mdb_names = claro_sql_get_main_tbl();
        $tbl_tracking_event  = $tbl_mdb_names['tracking_event'];

        $sql = "INSERT INTO `" . $tbl_tracking_event . "`
                SET `course_code` = " . ( is_null($cid) ? "NULL" : "'" . claro_sql_escape($cid) . "'" ) . ",
                    `tool_id` = ". ( is_null($tid) ? "NULL" : "'" . claro_sql_escape($tid) . "'" ) . ",
                    `user_id` = ". ( is_null($uid) ? "NULL" : "'" . claro_sql_escape($uid) . "'" ) . ",
                    `date` = '" . $date . "',
                    `type` = '" . claro_sql_escape($eventType) . "',
                    `data` = '" . claro_sql_escape($data) . "'";

        return claro_sql_query($sql);
    }


    public function trackToolAccess( $event )
    {
        if( ! get_conf('is_trackingEnabled') ) return false;

        $event_args = $event->getArgs();

        $cid        = array_key_exists('cid', $event_args) ? $event_args['cid'] : '';
        $tid        = array_key_exists('tid', $event_args) ? $event_args['tid'] : '';

        if( !empty($cid) && !empty($tid) )
        {
            // count access only if user has not already accessed this tool in this course during this session
            if( !isset( $_SESSION['tracking']['visitedTools'][$cid][$tid] ) )
            {
                $this->trackInCourse( $event );
                // TODO : also save information in main DB for later use from administration : cid, tid and counter ?
                $_SESSION['tracking']['visitedTools'][$cid][$tid] = claro_mktime();
                return true;
            }
        }

        return false;

    }

    public function trackCourseAccess( $event )
    {
        // tool_id will be recorded too if user enters via a tool directly
        if( ! get_conf('is_trackingEnabled') ) return false;

        $event_args = $event->getArgs();

        $cid        = array_key_exists('cid', $event_args) ? $event_args['cid'] : '';

        if( !empty($cid) )
        {
            // count access only if user has not already accessed this tool in this course during this session
            if( !isset( $_SESSION['tracking']['visitedCourses'][$cid] ) )
            {
                $this->trackInCourse( $event );
                // TODO : also save information in main DB for later use from administration : cid and counter ?
                $_SESSION['tracking']['visitedCourses'][$cid] = claro_mktime();
                // session destruction on login or logout
                return true;
            }
        }

        return false;
    }

    public function trackPlatformAccess( $event )
    {
        // tool_id will be recorded too if user enters via a tool directly
        if( ! get_conf('is_trackingEnabled') ) return false;

        // count access only if user was not already on the platform
        if( !isset( $_SESSION['tracking']['platformAccessed'] ) )
        {
            // we don't have a trace saying that user was already on the platform
            // so check its referer
            if( ! empty($_SERVER['HTTP_REFERER']) )
            {
                if( false === strpos( $_SERVER['HTTP_REFERER'],get_path('rootWeb') ) )
                {
                    // http referer is different user probably comes from outside
                    $externalReferer = true;
                }
                else
                {
                    $externalReferer = false;
                    $_SESSION['tracking']['platformAccessed'] = claro_mktime();
                }
            }
            else
            {
                // referer not set so we take the guess that user was not on the platform
                // and access it directly
                $externalReferer = true;
            }

            if( $externalReferer )
            {
                $this->trackInPlatform( $event );
                $_SESSION['tracking']['platformAccessed'] = claro_mktime();

                return true;
            }
        }
        return false;
    }

    /*
     * About the red ball
     */
    public function modificationDefault( $event )
    {
        $event_args = $event->getArgs();

        $cid        = array_key_exists( 'cid', $event_args) ? $event_args['cid'] : '';
        $tid        = array_key_exists( 'tid', $event_args) ? $event_args['tid'] : 0;
        $rid        = array_key_exists( 'rid', $event_args) ? $event_args['rid'] : '';
        $gid        = array_key_exists( 'gid', $event_args) ? $event_args['gid'] : 0;
        $uid        = array_key_exists( 'uid', $event_args) ? $event_args['uid'] : 0;

        $eventType  = $event->getEventType();

        // call function to update db info

        if ($eventType != 'delete')
        {
            $tbl_mdb_names = claro_sql_get_main_tbl();
            $tbl_notify    = $tbl_mdb_names['notify'];

            // 1- check if row already exists

            $sql = "SELECT count(`id`) FROM `" . $tbl_notify . "`
                         WHERE `course_code`= '".claro_sql_escape($cid)."'
                           AND `tool_id`= ". (int) $tid . "
                           AND `ressource_id`= '". claro_sql_escape($rid) . "'
                           AND `group_id` = ". (int) $gid . "
                           AND `user_id` = ". (int) $uid;

            $do_update = (bool) claro_sql_query_get_single_value($sql);

            // 2- update or create for concerned row

            $now = claro_date("Y-m-d H:i:s");

            if ($do_update)
            {
                $sqlDoUpdate = "UPDATE `" . $tbl_notify . "`
                     SET `date` = '" . claro_sql_escape($now) . "'
                     WHERE `course_code` = '" . claro_sql_escape($cid) . "'
                       AND `tool_id`     =  " . (int) $tid . "
                       AND `ressource_id`= '" . claro_sql_escape($rid) . "'
                       AND `group_id`    =  " . (int) $gid . "
                       AND `user_id`     =  " . (int) $uid;
            }
            else
            {
                $sqlDoUpdate = "INSERT INTO `" . $tbl_notify . "`
                            SET   `course_code`  = '" . claro_sql_escape($cid) . "',
                                  `tool_id`      =  " . (int) $tid . ",
                                  `date`         = '" . claro_sql_escape($now) . "',
                                  `ressource_id` = '" . claro_sql_escape($rid) . "',
                                  `group_id`     =  " . (int) $gid . ",
                                  `user_id`      =  " . (int) $uid ;

            }

            claro_sql_query($sqlDoUpdate);

            // 3- save in session of this user that this ressource is already seen.
            // --> as he did the modification himself, he shouldn't be notified
            $_SESSION['ConsultedRessourceList'][$cid . ':' . $tid . ':' . $gid . ':' . $rid . ':' . $now] = TRUE;

        }
    }

    public function modificationUpdate( $event )
    {
        $tbl_mdb_names = claro_sql_get_main_tbl();
        $tbl_notify     = $tbl_mdb_names['notify'];

        $event_args = $event->getArgs();

        $cid        = array_key_exists( 'cid', $event_args) ? $event_args['cid'] : '';
        $tid        = array_key_exists( 'tid', $event_args) ? $event_args['tid'] : 0;
        $resource   = array_key_exists( 'rid', $event_args) ? $event_args['rid'] : array();
        $gid        = array_key_exists( 'gid', $event_args) ? $event_args['gid'] : 0;
        $uid        = array_key_exists( 'uid', $event_args) ? $event_args['uid'] : 0;

        $eventType  = $event->getEventType();

        $oldResourceId = $resource['old_uri'];
        $newResourceId = $resource['new_uri'];

        // update ressource_id

        $sql = "UPDATE `" . $tbl_notify . "`
                SET `ressource_id`= '" . claro_sql_escape($newResourceId) . "'
                WHERE `course_code`='". claro_sql_escape($cid) ."'
                  AND `tool_id`= ". (int) $tid."
                  AND `ressource_id`= '". claro_sql_escape($oldResourceId) ."'
                  AND `group_id` = ". (int) $gid;

        claro_sql_query($sql);
    }

    /**
     *
     * TODO split this method in different callbacks
     */
    public function modificationDelete ( $event )
    {
        $tbl_mdb_names = claro_sql_get_main_tbl();
        $tbl_notify     = $tbl_mdb_names['notify'];

        $event_args = $event->getArgs();

        $cid        = array_key_exists( 'cid', $event_args) ? $event_args['cid'] : '';
        $tid        = array_key_exists( 'tid', $event_args) ? $event_args['tid'] : 0;
        $rid        = array_key_exists( 'rid', $event_args) ? $event_args['rid'] : '';
        $gid        = array_key_exists( 'gid', $event_args) ? $event_args['gid'] : 0;
        $uid        = array_key_exists( 'uid', $event_args) ? $event_args['uid'] : 0;

        $eventType  = $event->getEventType();

        // in case of a complete deletion of a COURSE, all event regarding this course must be deleted
        if ($eventType == 'course_deleted')
        {
            $sql = "DELETE FROM `" . $tbl_notify . "`
                    WHERE `course_code`='". claro_sql_escape($cid)."'";
        }

        // in case of a complete deletion of a GROUP, all event regarding this group must be deleted
        elseif ($eventType == 'group_deleted')
        {
            $sql = "DELETE FROM `" . $tbl_notify . "`
                    WHERE `course_code`='" . claro_sql_escape($cid) . "'
                      AND `group_id` = ". (int) $gid;
        }
        // otherwise, just delete event concerning the tool or the ressource in the course
        else
        {
            $sql = "DELETE FROM `" . $tbl_notify . "`
                      WHERE `course_code`='". claro_sql_escape($cid) ."'
                        AND `tool_id`= ". (int) $tid."
                        AND `ressource_id`= '". claro_sql_escape($rid) ."'
                        AND `group_id` = ". (int) $gid;
        }

        claro_sql_query($sql);
    }









    // get notification from database methods

    public function getNotifiedCourses( $date, $user_id )
    {
        $tbl_mdb_names = claro_sql_get_main_tbl();
        $tbl_cours_user = $tbl_mdb_names['rel_course_user'];
        $tbl_notify     = $tbl_mdb_names['notify'];

        $courses = array();
        $courseList = array();

        //  1- find the list of the user's course and in this list, take only the course where recent events happened
        //    A- FOR A STUDENT : where the events concerned everybody (uid = 0) or the user himself (uid)
        //    B- FOR A TEACHER : every events of a course must be reported (this take much sense in the work tool, with submissions)
        if ( !isset($_SESSION['firstLogin']) || !$_SESSION['firstLogin'] )
        {
            $sql="SELECT `course_code`, `tool_id`, `group_id`, `ressource_id`,`date` FROM `".$tbl_cours_user."` AS CU, `".$tbl_notify."` AS N
                WHERE CU.`code_cours` = N.`course_code`
                    AND CU.`user_id` = '". (int)$user_id."'
                    AND N.`date` > '".$date."'
                    AND ((N.`user_id` = '0' OR N.`user_id` = '". (int)$user_id."') OR CU.`isCourseManager`='1')
                    ";

            $courseList = claro_sql_query_fetch_all($sql);

        }

        // from result in the notify table, we mustn't take account of the ressources already consulted,
        // (saved in session)

        $size = count($courseList);

        for($i=0;$i<$size;$i++)
        {

           if (($courseList[$i]['group_id'])==0) $courseList[$i]['group_id'] = "";

           if (isset($_SESSION['ConsultedRessourceList'])
            && isset($_SESSION['ConsultedRessourceList'][$courseList[$i]['course_code'].":".$courseList[$i]['tool_id'].":".$courseList[$i]['group_id'].":".$courseList[$i]['ressource_id'].":".$courseList[$i]['date']]))
           {
              unset($courseList[$i]); // the ressource is already seen, we retrieve it from the results

           }
           else
           {
               $courses[] = $courseList[$i]['course_code']; // there is a ressource not consulted yet, we add the course_id in results
           }
        }

        //2- return an array with the courses with recent unknow event until the date '$date' in the course list of the user

        return $courses;
    }

    public function getNotifiedTools( $course_id, $date, $user_id,$group_id = '0' )
    {

        $tbl_mdb_names = claro_sql_get_main_tbl();
        $tbl_notify    = $tbl_mdb_names['notify'];

        //if user is course admin, he is notified of event concerning all user in the course

        if (claro_is_course_manager())
        {
           $toadd = "";
        } // otherwise we must only know about what concerns everybody or himself
        else
        {
           $toadd = "AND (N.`user_id` = '0' OR N.`user_id` = '".(int)$user_id."')";
        }


        $tools = array();

        // 1 - Find the tool list of the given course that contains some event newer than the date '$date'
        //    - FOR A STUDENT : where the events concerned everybody (uid = 0) or the user himself (uid)
        //    - FOR A TEACHER : every events of a course must be reported (this take much sense in the work tool, with submissions)


        if ( !isset($_SESSION['firstLogin']) || !$_SESSION['firstLogin'] ) {


            // A- retrieve new item since last login in the notify table

            $sql = "SELECT `tool_id`, `date`, `group_id`, `course_code`, `ressource_id`
                    FROM `".$tbl_notify."` AS N
                    WHERE N.`course_code` = '".claro_sql_escape($course_id)."'
                    AND N.`date` > '".$date."'
                    ".$toadd."
                    AND (N.`group_id` = '".$group_id."')
                    ";

            $toolList = claro_sql_query_fetch_all($sql);

            if (is_array($toolList))

            // B- from result in the notify table, we mustn't take the ressources already consulted, saved in session

            $size = count($toolList);

            for($i=0;$i<$size;$i++)
            {

               if (($toolList[$i]['group_id'])==0) $toolList[$i]['group_id'] = "";

               if (isset($_SESSION['ConsultedRessourceList'])
                && isset($_SESSION['ConsultedRessourceList'][$toolList[$i]['course_code'].":".$toolList[$i]['tool_id'].":".$toolList[$i]['group_id'].":".$toolList[$i]['ressource_id'].":".$toolList[$i]['date']]))
               {
                  unset($toolList[$i]); // the ressource is already seen, we retrieve it from the results

               }
               else
               {
                   $tools[] = $toolList[$i]['tool_id']; // there is a ressource not consulted yet, we add tht tool_id in results
               }
            }

        }


        // 2- return an array with the tools id with recent unknow event until the date '$date'

        return $tools;
    }

    public function getNotifiedGroups( $course_id, $date )
    {
        //1 - Find infiormation in Session and DB

        $tbl_mdb_names = claro_sql_get_main_tbl();
        $tbl_notify    = $tbl_mdb_names['notify'];

        $groups = array();

            // A- retrieve new item since last login in the notify table

        $sql = "SELECT `group_id`, `date`, `ressource_id`, `tool_id`, `course_code`
                    FROM `" . $tbl_notify . "` AS N
                    WHERE N.`course_code` = '" . claro_sql_escape($course_id) . "'
                    AND N.`date` > '" . claro_sql_escape($date) . "'
                    AND (N.`group_id` != '0')
                    GROUP BY `group_id`
                    ";
        $groupList = claro_sql_query_fetch_all($sql);

            // B- from result in the notify table, we mustn't take the ressources already consulted, saved in session

       $size = count($groupList);

       for($i=0; $i < $size; $i++)
       {
           if (isset($_SESSION['ConsultedRessourceList'])
           && isset($_SESSION['ConsultedRessourceList'][$groupList[$i]['course_code'] . ':' . $groupList[$i]['tool_id'] . ':' . $groupList[$i]['group_id'] . ':' . $groupList[$i]['ressource_id'] . ':' . $groupList[$i]['date']]))
           {
               unset($groupList[$i]); // the ressource is already seen, we retrieve it from the results
           }
           else
           {
               $groups[] = $groupList[$i]['group_id']; // there is a ressource not consulted yet, we add the group_id in results
           }
       }

       // 2- return an array with the group id with recent unknow event until the date '$date'

         return $groups;
    }

    public function isANotifiedRessource($course_id, $date, $user_id, $group_id, $tool_id, $ressourceId,$setAsViewed=TRUE)
    {
        // global $fileList, $fileKey; //needed for the document tool
        $keysStrings = $course_id . ':' . $tool_id . ':' . $group_id . ':';

        // see if the ressource is new AND not consulted yet

        if (!isset($this->toolNotifiedRessourceList))
        {
            $this->toolNotifiedRessourceList = $this->getNotifiedRessources($course_id, $date, $user_id, $group_id, $tool_id);
        }


        // compare table result with SESSION information

        if (isset($this->toolNotifiedRessourceList[$ressourceId])
             && !isset($_SESSION['ConsultedRessourceList'][$keysStrings . $ressourceId . ':' . $this->toolNotifiedRessourceList[$ressourceId]['date']]))

        {
            //now, the ressource is seen

            if ($setAsViewed) $_SESSION['ConsultedRessourceList'][$keysStrings . $ressourceId . ':' . $this->toolNotifiedRessourceList[$ressourceId]['date']] = TRUE;

            return true;
        }
        else return false;
    }
    
    public function isANotifiedDocument($course_id, $date, $user_id, $group_id, $tool_id, $thisFile,$setAsViewed=TRUE)
    {
        // global $fileList, $fileKey; //needed for the document tool
        $keysStrings = $course_id . ':' . $tool_id . ':' . $group_id . ':';

        // see if the ressource is new AND not consulted yet

        if (!isset($this->toolNotifiedRessourceList))
        {
            $this->toolNotifiedRessourceList = $this->getNotifiedRessources($course_id, $date, $user_id, $group_id, $tool_id);
        }

        //deal with specific case of folders in document tool

        if ((claro_get_current_course_tool_data('label') == 'CLDOC') && ($thisFile['type'] == A_DIRECTORY))
        {
            $ressourceList = $this->toolNotifiedRessourceList;

            foreach ($ressourceList as $ressource)
            {
                $ressource_identification = $keysStrings
                .                           $ressource['ressource_id'] . ':'
                .                           $ressource['date']
                ;
                $pattern = '/' . $keysStrings . preg_quote($thisFile['path'], '/') . '.*:' . $ressource['date'] . '/';

                if (!isset($_SESSION['ConsultedRessourceList'][$ressource_identification])
                && preg_match($pattern,$ressource_identification))
                {
                    if ($ressource_identification == $keysStrings . $thisFile['path'] . ':' . $ressource['date'])
                    //in case the new item is the folder itself only
                    {
                        $_SESSION['ConsultedRessourceList'][$ressource_identification] = TRUE;
                    }
                    return true;
                }
            }
            return false;
        }


        // compare table result with SESSION information

        if (isset($this->toolNotifiedRessourceList[$thisFile['path']])
             && !isset($_SESSION['ConsultedRessourceList'][$keysStrings . $thisFile['path'] . ':' . $this->toolNotifiedRessourceList[$thisFile['path']]['date']]))

        {
            //now, the ressource is seen

            if ($setAsViewed) $_SESSION['ConsultedRessourceList'][$keysStrings . $thisFile['path'] . ':' . $this->toolNotifiedRessourceList[$thisFile['path']]['date']] = TRUE;

            return true;
        }
        else return false;
    }

    public function isANotifiedForum( $course_id, $date, $user_id, $group_id, $tool_id, $forumId )
    {

        $keysStrings = $course_id . ':' . $tool_id . ':' . $group_id . ':';

        // see if the ressource is new AND not consulted yet

        $notified_ressources = $this->getNotifiedRessources($course_id, $date, $user_id, $group_id, $tool_id);

        // see if the forum is to be notified or not.

        foreach ($notified_ressources as $ressource)
        {
            $ressource_identification = $keysStrings . $ressource['ressource_id'] . ':' . $ressource['date'];

            $pattern = '/' . $keysStrings . $forumId . '-.*:' . $ressource['date'] . '/';

            //for each ressource, it must not be in session yet and it must concern this forum exactly

            if (!isset($_SESSION['ConsultedRessourceList'][$ressource_identification])
            && preg_match($pattern, $ressource_identification))
            {
                return true;
            }
        }
        return false;
    }

    public function getNotifiedRessources( $course_id, $date, $user_id, $gid, $tid )
    {
        $tbl_mdb_names = claro_sql_get_main_tbl();
        $tbl_notify    = $tbl_mdb_names['notify'];

        $ressources = array();

        if ( !isset($_SESSION['firstLogin']) || !$_SESSION['firstLogin'] )
        {
            $sql = "SELECT `ressource_id`, `date`
                    FROM `" . $tbl_notify . "` AS N
                    WHERE  N.`course_code`     = '" . claro_sql_escape($course_id) . "'
                      AND  N.`date`            > '" . claro_sql_escape($date) . "'
                      AND (N.`user_id`  = '0' OR N.`user_id`  = " . (int) $user_id . ")
                      AND (N.`group_id` = '0' OR N.`group_id` = " . (int) $gid . ")
                      AND (N.`tool_id`  = '" . claro_sql_escape($tid) . "')";
            $ressourceList = claro_sql_query_fetch_all($sql);

            foreach($ressourceList as $ressourceItem)
            {
                $ressources[$ressourceItem['ressource_id']] = $ressourceItem;
            }

        }

        return $ressources;

    }

    public function getLastLoginBeforeToday($user_id)
    {
        $tbl_mdb_names        = claro_sql_get_main_tbl();
        $tbl_tracking_event   = $tbl_mdb_names['tracking_event'];

        $today = date('Y-m-d 00:00:00');

        $sql = "SELECT MAX(`date`) AS THEDAY
                  FROM `" . $tbl_tracking_event . "`
                 WHERE `type` = 'user_login'
                   AND `user_id` = " . (int) $user_id . "
                   AND `date` < '" . $today . "'";

        $theday = claro_sql_query_get_single_value($sql);

        $login_date = ($theday ? $theday : $today);

        return $login_date;
    }

    public function getNotificationDate($user_id)
    {
        return $this->getLastActionBeforeLoginDate($user_id);
    }

    public function getLastActionBeforeLoginDate($user_id)
    {
        $tbl_mdb_names = claro_sql_get_main_tbl();
        $tbl_rel_course_user = $tbl_mdb_names['rel_course_user'];
        
        $_user = claro_get_current_user_data();

        //if we already knwo in session what is the last action date, just retrieve it from the session

        if (isset($_SESSION['last_action'])) return $_SESSION['last_action'];

        //otherwise we must find it in the access statistics information of the database

        $last_login_date = date("Y-m-d H:i:s", $_user['lastLogin']);

        // 1 - retriev course list in which the user is subscribed

        $sql  = "SELECT `code_cours`
                   FROM `".$tbl_rel_course_user."` AS CU
                  WHERE CU.`user_id`='".(int)$user_id."'";
        $courses = claro_sql_query_fetch_all($sql);

        // 2 - retrieve each max(date) of acces for each date

        $last_action_date = "0000-00-00 00:00:00"; //set default last action date

            //look for last action date in every tracking table of courses where the user is registered

        foreach ($courses as $course)
        {

            $tbl_c_names = claro_sql_get_course_tbl(claro_get_course_db_name_glued($course['code_cours']));
            $tbl_course_tracking_event = $tbl_c_names['tracking_event'];  
            
            $sqlMaxDate = "SELECT MAX(`date`) AS MAXDATE
                      FROM `" . $tbl_course_tracking_event . "` AS STAT,
                           `" . $tbl_rel_course_user . "` AS CU
                     WHERE `type` = 'course_access'
                       AND STAT.`user_id` = " . (int) $user_id . "
                       AND STAT.`user_id` = CU.`user_id`
                       AND CU.`user_id` = " . (int) $user_id;
            $maxDate = claro_sql_query_get_single_value($sqlMaxDate);


            if ($maxDate && (strtotime($maxDate) > strtotime($last_action_date)))
            {
                $last_action_date = $maxDate;
            }
        }

        // return (and save in session) the latest action :
        // last login or latest action in the tracking tables

        if (strtotime($last_action_date) > strtotime($last_login_date))
        {
            $_SESSION['last_action'] = $last_action_date;
            return $last_action_date;
        }
        else
        {
            $_SESSION['last_action'] = $last_login_date;
            return $last_login_date;
        }
    }

    // aliases TODO rename in all scripts !!!!

    public function get_notified_courses( $date, $user_id )
    {
        return $this->getNotifiedCourses( $date, $user_id );
    }

    public function get_notified_tools( $course_id, $date, $user_id,$group_id = '0' )
    {
        return $this->getNotifiedTools( $course_id, $date, $user_id, $group_id );
    }

    public function get_notified_groups( $course_id, $date )
    {
        return $this->getNotifiedGroups( $course_id, $date );
    }

    public function is_a_notified_ressource($course_id, $date, $user_id, $group_id, $tool_id, $ressourceId,$setAsViewed=TRUE)
    {
        return $this->isANotifiedRessource($course_id, $date, $user_id, $group_id, $tool_id, $ressourceId,$setAsViewed);
    }

    public function is_a_notified_forum( $course_id, $date, $user_id, $group_id, $tool_id, $forumId )
    {
        return $this->isANotifiedForum( $course_id, $date, $user_id, $group_id, $tool_id, $forumId );
    }
    
    public function is_a_notified_document( $course_id, $date, $user_id, $group_id, $tool_id, $fileInfo )
    {
        return $this->isANotifiedDocument( $course_id, $date, $user_id, $group_id, $tool_id, $fileInfo );
    }

    public function get_notified_ressources( $course_id, $date, $user_id, $gid, $tid )
    {
        return $this->getNotifiedRessources( $course_id, $date, $user_id, $gid, $tid );
    }

    public function get_last_login_before_today($user_id)
    {
        return $this->getLastLoginBeforeToday($user_id);
    }

    public function get_notification_date($user_id)
    {
        return $this->getLastActionBeforeLoginDate($user_id);
    }

    public function get_last_action_before_login_date($user_id)
    {
        return $this->getLastActionBeforeLoginDate($user_id);
    }
}
