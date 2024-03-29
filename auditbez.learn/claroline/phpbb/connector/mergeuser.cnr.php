<?php

class CLFRM_MergeUser implements Module_MergeUser
{
    public function mergeCourseUsers( $uidToRemove, $uidToKeep, $courseId )
    {
        $moduleCourseTbl = get_module_course_tbl( array('bb_posts', 'bb_topics', 'bb_priv_msgs', 'bb_rel_forum_userstonotify', 'bb_rel_topic_userstonotify'), $courseId );
        
        $userToKeepProp = user_get_properties( $uidToKeep );
        
        $sql = "UPDATE `{$moduleCourseTbl['bb_posts']}`
                SET     poster_id = ".(int)$uidToKeep.",
                        nom = '". htmlspecialchars( $userToKeepProp['lastname'] ) . "',
                        prenom = '". htmlspecialchars( $userToKeepProp['firstname'] ) . "'
                WHERE poster_id = ".(int)$uidToRemove;

        if ( ! claro_sql_query($sql) )
        {
            throw new Exception("Cannot update bb_posts in {$courseId}");
        }
        
        // Update topic poster, lastname & firstname
        $sql = "UPDATE `{$moduleCourseTbl['bb_topics']}`
                SET topic_poster = " . (int)$uidToKeep . ",
                nom = '".htmlspecialchars( $userToKeepProp['lastname']) . "',
                prenom = '".htmlspecialchars( $userToKeepProp['firstname']) . "'
                WHERE topic_poster = ".(int)$uidToRemove;
        
        if( ! claro_sql_query($sql) )
        {
            echo mysql_error();
            
            throw new Exception("Cannot update bb_topics in {$courseId}");
        }
        
        // Update private messages (from)
        $sql = "UPDATE `{$moduleCourseTbl['bb_priv_msgs']}`
                SET from_userid = " . (int)$uidToKeep . "
                WHERE from_userid = " . (int)$uidToRemove;
        
        if( ! claro_sql_query($sql) )
        {
            throw new Exception("Cannot update bb_priv_msgs in {$courseId}");
        }
        
        // Update private messages (to)
        $sql = "UPDATE `{$moduleCourseTbl['bb_priv_msgs']}`
                SET to_userid = " . (int)$uidToKeep . "
                WHERE to_userid = " . (int)$uidToRemove;
        
        if( ! claro_sql_query($sql) )
        {
            throw new Exception("Cannot update bb_priv_msgs in {$courseId}");
        }
        
        
        // Update topic notification        
        $sql = "SELECT `topic_id`
                FROM `{$moduleCourseTbl['bb_rel_topic_userstonotify']}`
                WHERE `user_id` = " . (int)$uidToRemove;        
        
        $topicIds = claro_sql_query_fetch_all($sql);        
        
        if( !empty( $topicIds) )
        {
            foreach( $topicIds as $_topicId)
            {
                $topicId = $_topicId['topic_id'];
                $sql = "SELECT `notify_id`
                        FROM `{$moduleCourseTbl['bb_rel_topic_userstonotify']}`
                        WHERE `user_id` = ".(int)$uidToRemove." AND `topic_id` = ".(int)$topicId . "
                        LIMIT 1";
                
                $notify = claro_sql_query_get_single_row($sql);
                
                if( !empty($notify) )
                {
                    // Update notification for userToRemove to userToKeep
                    $sql = "UPDATE `{$moduleCourseTbl['bb_rel_topic_userstonotify']}`
                            SET user_id = ". (int)$uidToKeep. "
                            WHERE notify_id = " . (int) $notify['notify_id'];
                
                    if( ! claro_sql_query($sql) )
                    {
                        throw new Exception("Cannot update bb_rel_topic_userstonotify in {$courseId}");
                    }
                }
                // Delete the notification for userToRemove
                $sql = "DELETE FROM `{$moduleCourseTbl['bb_rel_topic_userstonotify']}` WHERE `user_id` = " . (int) $uidToRemove;
                
                if( ! claro_sql_query($sql) )
                {
                    throw new Exception("Cannot delete bb_rel_topic_userstonotify in {$courseId}");
                }                
            }
        }
        
        // Update forum notification        
        $sql = "SELECT `forum_id`
                FROM `{$moduleCourseTbl['bb_rel_forum_userstonotify']}`
                WHERE `user_id` = " . (int)$uidToRemove;
        
        $forumIds = claro_sql_query_fetch_all($sql);
        
        if( !empty( $forumIds) )
        {
            foreach( $forumIds as $_forumId)
            {
                $forumId = $_forumId['forum_id'];
                $sql = "SELECT `notify_id`
                        FROM `{$moduleCourseTbl['bb_rel_forum_userstonotify']}`
                        WHERE `user_id` = ".(int)$uidToRemove." AND `forum_id` = ".(int)$forumId . "
                        LIMIT 1";
                
                $notify = claro_sql_query_get_single_row($sql);
                
                if( !empty($notify) )
                {
                    // Update notification for userToRemove to userToKeep
                    $sql = "UPDATE `{$moduleCourseTbl['bb_rel_forum_userstonotify']}`
                            SET user_id = ". (int)$uidToKeep. "
                            WHERE notify_id = " . (int) $notify['notify_id'];
                
                    if( ! claro_sql_query($sql) )
                    {
                        throw new Exception("Cannot update bb_rel_forum_userstonotify in {$courseId}");
                    }
                }
                // Delete the notification for userToRemove
                $sql = "DELETE FROM `{$moduleCourseTbl['bb_rel_form_userstonotify']}` WHERE `user_id` = " . (int) $uidToRemove;
                
                if( ! claro_sql_query($sql) )
                {
                    throw new Exception("Cannot delete bb_rel_forum_userstonotify in {$courseId}");
                }
                
            }
        }
        
        
        
    }
    
    public function mergeUsers( $uidToRemove, $uidToKeep )
    {
        // empty
    }
}
