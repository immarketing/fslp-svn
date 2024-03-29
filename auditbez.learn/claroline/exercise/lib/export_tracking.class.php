<?php // $Id: export_tracking.class.php 11843 2009-07-14 14:50:07Z ffervaille $
if ( count( get_included_files() ) == 1 ) die( '---' );
/*
+----------------------------------------------------------------------+
| CLAROLINE 1.6                                                        |
+----------------------------------------------------------------------+
| Copyright (c) 2001-2006 Universite catholique de Louvain (UCL)      |
+----------------------------------------------------------------------+
|   This program is free software; you can redistribute it and/or
|   modify it under the terms of the GNU General Public License
|   as published by the Free Software Foundation; either version 2
|   of the License, or (at your option) any later version.
+----------------------------------------------------------------------+
| Authors: Sébastien Piraux
+----------------------------------------------------------------------+
*/

include_once get_path('incRepositorySys') . '/lib/csv.class.php';

include_once dirname(__FILE__) . '/question.class.php';

class csvTrackTrueFalse extends csv
{
    var $question;
    var $exId;
    
    function csvTrackTrueFalse($question, $exId = '')
    {
        parent::csv(); // call constructor of parent class
        
        $this->question = $question;
        $this->exId = $exId;
    }
    
    function buildRecords()
    {
        $tbl_mdb_names = claro_sql_get_main_tbl();
        $tbl_user = $tbl_mdb_names['user'];
        
        
        $tbl_cdb_names = get_module_course_tbl( array( 'qwz_exercise', 'qwz_rel_exercise_question', 'qwz_tracking', 'qwz_tracking_questions', 'qwz_tracking_answers' ), claro_get_current_course_id() );
        
        $tbl_quiz_rel_test_question = $tbl_cdb_names['qwz_rel_exercise_question'];
        $tbl_quiz_question = $this->question->tblQuestion;
        
        $tbl_qwz_tracking = $tbl_cdb_names['qwz_tracking'    ];
        $tbl_qwz_tracking_questions = $tbl_cdb_names['qwz_tracking_questions'    ];
        $tbl_qwz_tracking_answers = $tbl_cdb_names['qwz_tracking_answers'    ];

        
        
        // this query doesn't show attempts without any answer
        $sql = "SELECT `TE`.`date`,
                        CONCAT(`U`.`prenom`,' ',`U`.`nom`) AS `name`,
                        `Q`.`title`,
                        `TEA`.`answer`
                FROM (
                    `".$tbl_quiz_question."` AS `Q`,
                    `".$tbl_quiz_rel_test_question."` AS `RTQ`,
                    `".$tbl_qwz_tracking."` AS `TE`,
                    `".$tbl_qwz_tracking_questions."` AS `TED`,
                    `".$tbl_user."` AS `U`
                    )
                LEFT JOIN `".$tbl_qwz_tracking_answers."` AS `TEA`
                    ON `TEA`.`details_id` = `TED`.`id`
                WHERE `RTQ`.`questionId` = `Q`.`id`
                    AND `RTQ`.`exerciseId` = `TE`.`exo_id`
                    AND `TE`.`id` = `TED`.`exercise_track_id`
                    AND `U`.`user_id` = `TE`.`user_id`
                    AND `TED`.`question_id` = `Q`.`id`
                    AND `Q`.`id` = ".$this->question->getId();

        if( !empty($this->exerciseId) ) $sql .= " AND `RTQ`.`exercice_id` = ".$this->exerciseId;

        $sql .= " ORDER BY `TE`.`date` ASC, `name` ASC";

        $attempts = claro_sql_query_fetch_all($sql);

        // build recordlist with good values for answers
        if( is_array($attempts) )
        {
            $i = 0;
            foreach( $attempts as $attempt )
            {
                $this->recordList[$i] = $attempt;

                if( $attempt['answer'] == 'TRUE' )
                    $this->recordList[$i]['answer'] = get_lang('True');
                elseif( $attempt['answer'] == 'FALSE' )
                    $this->recordList[$i]['answer'] = get_lang('False');
                else
                    $this->recordList[$i]['answer'] = '';

                $i++;
            }

            if( isset($this->recordList) && is_array($this->recordList) ) return true;
        }
        
        return false;
    }
}



class csvTrackMultipleChoice extends csv
{
    var $question;
    var $exId;

    function csvTrackMultipleChoice($question, $exId = '')
    {
        parent::csv(); // call constructor of parent class
        
        $this->question = $question;
        $this->exId = $exId;
    }
    
    // build : date;username;statement;answer
    function buildRecords()
    {
        $tbl_mdb_names = claro_sql_get_main_tbl();
        $tbl_user = $tbl_mdb_names['user'];

        $tbl_cdb_names = get_module_course_tbl( array( 'qwz_exercise', 'qwz_rel_exercise_question', 'qwz_tracking', 'qwz_tracking_questions', 'qwz_tracking_answers' ), claro_get_current_course_id() );
        
        $tbl_quiz_rel_test_question = $tbl_cdb_names['qwz_rel_exercise_question'];
        $tbl_quiz_question = $this->question->tblQuestion;
        
        $tbl_qwz_tracking = $tbl_cdb_names['qwz_tracking'    ];
        $tbl_qwz_tracking_questions = $tbl_cdb_names['qwz_tracking_questions'    ];
        $tbl_qwz_tracking_answers = $tbl_cdb_names['qwz_tracking_answers'    ];


        // this query doesn't show attempts without any answer
        $sql = "SELECT `TE`.`date`,
                        CONCAT(`U`.`prenom`,' ',`U`.`nom`) AS `name`,
                        `Q`.`title`,
                        `TEA`.`answer`
                FROM (
                    `".$tbl_quiz_question."` AS `Q`,
                    `".$tbl_quiz_rel_test_question."` AS `RTQ`,
                    `".$tbl_qwz_tracking."` AS `TE`,
                    `".$tbl_qwz_tracking_questions."` AS `TED`,
                    `".$tbl_user."` AS `U`
                    )
                LEFT JOIN `".$tbl_qwz_tracking_answers."` AS `TEA`
                    ON `TEA`.`details_id` = `TED`.`id`
                WHERE `RTQ`.`questionId` = `Q`.`id`
                    AND `RTQ`.`exerciseId` = `TE`.`exo_id`
                    AND `TE`.`id` = `TED`.`exercise_track_id`
                    AND `U`.`user_id` = `TE`.`user_id`
                    AND `TED`.`question_id` = `Q`.`id`
                    AND `Q`.`id` = ".$this->question->getId();

        if( !empty($this->exerciseId) ) $sql .= " AND `RTQ`.`exercice_id` = ".$this->exerciseId;

        $sql .= " ORDER BY `TE`.`date` ASC, `name` ASC";

        $attempts = claro_sql_query_fetch_all($sql);

        if( is_array($attempts) )
        {
            // build recordlist with good values for answers
            $i = 0;
            foreach( $attempts as $attempt )
            {
                $this->recordList[$i] = $attempt;
                $i++;
            }

            if( isset($this->recordList) && is_array($this->recordList) )
                return true;
            else
                return false;
        }
        
        return false;
    }
}


class csvTrackFIB extends csv
{
    var $question;
    var $exerciseId;

    function csvTrackFIB($question, $exId = '')
    {
        parent::csv(); // call constructor of parent class
        
        $this->question = $question;
        $this->exId = $exId;
    }
    
    // build : date;username;statement;answer
    function buildRecords()
    {
        $tbl_mdb_names = claro_sql_get_main_tbl();
        $tbl_user = $tbl_mdb_names['user'];

        $tbl_cdb_names = get_module_course_tbl( array( 'qwz_exercise', 'qwz_rel_exercise_question', 'qwz_tracking', 'qwz_tracking_questions', 'qwz_tracking_answers' ), claro_get_current_course_id() );
        
        $tbl_quiz_rel_test_question = $tbl_cdb_names['qwz_rel_exercise_question'];
        $tbl_quiz_question = $this->question->tblQuestion;
        
        $tbl_qwz_tracking = $tbl_cdb_names['qwz_tracking'    ];
        $tbl_qwz_tracking_questions = $tbl_cdb_names['qwz_tracking_questions'    ];
        $tbl_qwz_tracking_answers = $tbl_cdb_names['qwz_tracking_answers'    ];

        // this query doesn't show attempts without any answer
        $sql = "SELECT `TE`.`date`,
                        CONCAT(`U`.`prenom`,' ',`U`.`nom`) AS `name`,
                        `Q`.`title`,
                        `TEA`.`answer`
                FROM (
                    `".$tbl_quiz_question."` AS `Q`,
                    `".$tbl_quiz_rel_test_question."` AS `RTQ`,
                    `".$tbl_qwz_tracking."` AS `TE`,
                    `".$tbl_qwz_tracking_questions."` AS `TED`,
                    `".$tbl_user."` AS `U`
                    )
                LEFT JOIN `".$tbl_qwz_tracking_answers."` AS `TEA`
                    ON `TEA`.`details_id` = `TED`.`id`
                WHERE `RTQ`.`questionId` = `Q`.`id`
                    AND `RTQ`.`exerciseId` = `TE`.`exo_id`
                    AND `TE`.`id` = `TED`.`exercise_track_id`
                    AND `U`.`user_id` = `TE`.`user_id`
                    AND `TED`.`question_id` = `Q`.`id`
                    AND `Q`.`id` = ".$this->question->getId();

        if( !empty($this->exerciseId) ) $sql .= " AND `RTQ`.`exercice_id` = ".$this->exerciseId;

        $sql .= " ORDER BY `TE`.`date` ASC, `name` ASC";

        $attempts = claro_sql_query_fetch_all($sql);

        if( is_array($attempts) )
        {
            // build recordlist with good values for answers
            $i = 0;
            foreach( $attempts as $attempt )
            {
                $this->recordList[$i] = $attempt;
                $i++;
            }

            if( isset($this->recordList) && is_array($this->recordList) )
                return true;
            else
                return false;
        }
        
        return false;
    }
}

class csvTrackMatching extends csv
{
    var $question;
    var $exerciseId;

    function csvTrackMatching($question, $exId = '')
    {
        parent::csv(); // call constructor of parent class
        
        $this->question = $question;
        $this->exId = $exId;
    }
    
    // build : date;username;statement;answer
    function buildRecords()
    {
        $tbl_mdb_names = claro_sql_get_main_tbl();
        $tbl_user = $tbl_mdb_names['user'];

        $tbl_cdb_names = get_module_course_tbl( array( 'qwz_exercise', 'qwz_rel_exercise_question', 'qwz_tracking', 'qwz_tracking_questions', 'qwz_tracking_answers' ), claro_get_current_course_id() );
        
        $tbl_quiz_rel_test_question = $tbl_cdb_names['qwz_rel_exercise_question'];
        $tbl_quiz_question = $this->question->tblQuestion;
        
        $tbl_qwz_tracking = $tbl_cdb_names['qwz_tracking'    ];
        $tbl_qwz_tracking_questions = $tbl_cdb_names['qwz_tracking_questions'    ];
        $tbl_qwz_tracking_answers = $tbl_cdb_names['qwz_tracking_answers'    ];

        // this query doesn't show attempts without any answer
        $sql = "SELECT `TE`.`date`,
                        CONCAT(`U`.`prenom`,' ',`U`.`nom`) AS `name`,
                        `Q`.`title`,
                        `TEA`.`answer`
                FROM (
                    `".$tbl_quiz_question."` AS `Q`,
                    `".$tbl_quiz_rel_test_question."` AS `RTQ`,
                    `".$tbl_qwz_tracking."` AS `TE`,
                    `".$tbl_qwz_tracking_questions."` AS `TED`,
                    `".$tbl_user."` AS `U`
                    )
                LEFT JOIN `".$tbl_qwz_tracking_answers."` AS `TEA`
                    ON `TEA`.`details_id` = `TED`.`id`
                WHERE `RTQ`.`questionId` = `Q`.`id`
                    AND `RTQ`.`exerciseId` = `TE`.`exo_id`
                    AND `TE`.`id` = `TED`.`exercise_track_id`
                    AND `U`.`user_id` = `TE`.`user_id`
                    AND `TED`.`question_id` = `Q`.`id`
                    AND `Q`.`id` = ".$this->question->getId();

        if( !empty($this->exerciseId) ) $sql .= " AND `RTQ`.`exercice_id` = ".$this->exerciseId;

        $sql .= " ORDER BY `TE`.`date` ASC, `name` ASC";

        $attempts = claro_sql_query_fetch_all($sql);

        if( is_array($attempts) )
        {
            // build recordlist with good values for answers
            $i = 0;
            foreach( $attempts as $attempt )
            {
                $this->recordList[$i] = $attempt;
                $i++;
            }

            if( isset($this->recordList) && is_array($this->recordList) )
                return true;
            else
                return false;
        }
        
        return false;
    }
}

/**
 * @return string csv data or empty string
 *
 */
function export_question_tracking($quId, $exId = '')
{
    $question = new Question();
    if( !$question->load($quId) )
    {
        return "";
    }

    switch($question->getType())
    {
        case 'TF': 
            $cvsTrack = new csvTrackTrueFalse($question, $exId);
            break;
        case 'MCUA':
        case 'MCMA':
            $cvsTrack = new csvTrackMultipleChoice($question, $exId);
            break;
        case 'FIB':
            $cvsTrack = new csvTrackFIB($question, $exId);
            break;
        case 'MATCHING':
            $cvsTrack = new csvTrackMatching($question, $exId);
            break;
        default:
            break;
    }

    if( isset($cvsTrack) )
    {
        $cvsTrack->buildRecords();
        return $cvsTrack->export();
    }
    else
    {
        return "";
    }
}

function export_exercise_tracking($exId)
{
    $exercise = new Exercise();
    if( !$exercise->load($exId) )
    {
        return "";
    }

    $questionList = $exercise->getQuestionList();
    
    $exerciseCsv = '';
    foreach( $questionList as $question )
    {
        $exerciseCsv .= export_question_tracking($question['id'], $exId);
    }

    return $exerciseCsv;
}


//TODO? (concerning the code below) put into another file?

/**
 * Exports the students's result for an exercise into a csv file
 * Shows the overall result for each student
 * @author frederic.fervaille@uclouvain.be
 */
class ExoExportByUser extends csv
{
    /**
     * Contructor
     * @param int $exId the exercise id
    */
    public function __construct( $exId )
    {
        parent::csv(); // call parent class's constructor
        
        $this->exId = $exId;
    }
    
    /**
     * Builds the csv export
     * @return a string in csv format
    */
    public function buildCsv()
    {
        $tbl_mdb_names = claro_sql_get_main_tbl();
        $tbl_cdb_names = get_module_course_tbl( array( 'qwz_exercise','qwz_tracking' ), claro_get_current_course_id() );
        
        $tbl_user            = $tbl_mdb_names['user'];
        $tbl_rel_course_user = $tbl_mdb_names['rel_course_user'];
        $tbl_qwz_exercise    = $tbl_cdb_names['qwz_exercise'];
        $tbl_qwz_tracking    = $tbl_cdb_names['qwz_tracking'];
        
        $sql = "SELECT
                    CONCAT(`U`.`prenom`,' ',`U`.`nom`) AS `name`,
                    MIN(TE.`result`) AS `minimum`,
                    MAX(TE.`result`) AS `maximum`,
                    AVG(TE.`result`) AS `average`,
                    COUNT(TE.`result`) AS `attempts`,
                    AVG(TE.`time`) AS `avgTime`
                FROM
                    (`".$tbl_user."` AS `U`,
                     `".$tbl_rel_course_user."` AS `CU`,
                     `".$tbl_qwz_exercise."` AS `QT`)
                LEFT JOIN
                    `".$tbl_qwz_tracking."` AS `TE`
                    ON `CU`.`user_id` = `TE`.`user_id`
                    AND `QT`.`id` = `TE`.`exo_id`
                WHERE `CU`.`user_id` = `U`.`user_id`
                AND `CU`.`code_cours` = '" . claro_sql_escape(claro_get_current_course_id()) . "'
                AND (
                    `TE`.`exo_id` = ". claro_sql_escape($this->exId)."
                    OR
                    `TE`.`exo_id` IS NULL
                )
                GROUP BY `U`.`user_id`
                ORDER BY `U`.`nom` DESC";
        
        $csvDatas = claro_sql_query_fetch_all($sql);
        
        $i = 0;
        
        foreach( $csvDatas as $csvLine )
        {
            if ( $csvLine[ 'attempts' ] == 0 )
            {
                $csvDatas[ $i ][ 'minimum' ] = '-';
                $csvDatas[ $i ][ 'maximum' ] = '-';
                $csvDatas[ $i ][ 'average' ] = '-';
                $csvDatas[ $i ][ 'avgTime' ] = '-';
            }
            else
            {
                $csvDatas[ $i ][ 'average' ] = (float)( round( $csvLine[ 'average' ]*100)/100);
                $csvDatas[ $i ][ 'avgTime' ] = claro_html_duration( floor( $csvLine[ 'avgTime' ] ) );
            }
            
            $i++;
        }
        
        $csvDatas[] = array(    'name' => get_lang( 'Student' ),
                                'minimum' => get_lang( 'Worst score' ),
                                'maximum' => get_lang( 'Best score' ),
                                'average' => get_lang( 'Average score' ),
                                'attempts' => get_lang( 'Attempts' ),
                                'avgTime' => get_lang( 'Average Time' ) );
        
        $this->recordList = array_reverse( $csvDatas );
        
        return $this->export();
    }
}

/**
 * Exports the students's result for an exercise into a csv file
 * Shows for each question : the best score, the worst score and the average score
 * @author frederic.fervaille@uclouvain.be
 */
class ExoExportByQuestion extends csv
{
    /**
     * Contructor
     * @param int $exId the exercise id
    */
    public function __construct( $exId )
    {
        parent::csv(); // call parent class's constructor
        
        $this->exId = $exId;
    }
    
    /**
     * Builds the csv export
     * @return a string in csv format
    */
    public function buildCsv()
    {
        $tbl_cdb_names = get_module_course_tbl( array( 'qwz_question',
                                                       'qwz_rel_exercise_question',
                                                       'qwz_tracking',
                                                       'qwz_tracking_questions' ),
                                                claro_get_current_course_id() );
        
        $tbl_qwz_question              = $tbl_cdb_names['qwz_question'];
        $tbl_qwz_tracking              = $tbl_cdb_names['qwz_tracking'];
        $tbl_qwz_rel_exercise_question = $tbl_cdb_names['qwz_rel_exercise_question'];
        $tbl_qwz_tracking_questions    = $tbl_cdb_names['qwz_tracking_questions'];
        
        $sql = "SELECT
                    `Q`.`title`,
                    `Q`.`grade`,
                    MIN(TED.`result`) AS `minimum`,
                    MAX(TED.`result`) AS `maximum`,
                    AVG(TED.`result`) AS `average`
                FROM (
                    `".$tbl_qwz_question."` AS `Q`,
                    `".$tbl_qwz_rel_exercise_question."` AS `RTQ`)
                LEFT JOIN `".$tbl_qwz_tracking."` AS `TE`
                    ON `TE`.`exo_id` = `RTQ`.`exerciseId`
                LEFT JOIN `".$tbl_qwz_tracking_questions."` AS `TED`
                    ON `TED`.`exercise_track_id` = `TE`.`id`
                    AND `TED`.`question_id` = `Q`.`id`
                WHERE `Q`.`id` = `RTQ`.`questionId`
                    AND `RTQ`.`exerciseId` = ". claro_sql_escape($this->exId)."
                GROUP BY `Q`.`id`
                ORDER BY `RTQ`.`rank` DESC";
        
        $csvDatas = claro_sql_query_fetch_all($sql);
        
        $i = 0;
        
        foreach ( $csvDatas as $csvLine )
        {
            if ( $csvLine['minimum'] == '' )
            {
                $csvDatas[ $i ]['minimum'] = 0;
                $csvDatas[ $i ]['maximum'] = 0;
            }
            
            $csvDatas[ $i ][ 'average' ] = (float)( round( $csvLine['average']*100 )/100 );
            
            str_replace( ',' , '' , $csvDatas[ $i ][ 'title' ] );
            
            $i++;
        }
        
        $csvDatas[] = array (   'title' => get_lang( 'Question title' ),
                                'grade' => get_lang( 'Maximum score' ),
                                'minimum' => get_lang( 'Worst score' ),
                                'maximum' => get_lang( 'Best score' ),
                                'average' => get_lang( 'Average score' ) );
        
        $this->recordList = array_reverse( $csvDatas );
        
        return $this->export();
    }
}