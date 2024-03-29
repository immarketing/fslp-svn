<?php // $Id: CLQWZ.def.conf.inc.php 11074 2008-09-01 09:34:44Z jrm_ $
if ( count( get_included_files() ) == 1 ) die( '---' );
/**
 * CLAROLINE
 *
 * This file describe the parameter for user tool
 *
 * @version 1.8 $Revision: 11074 $
 *
 * @copyright 2001-2006 Universite catholique de Louvain (UCL)
 *
 * @license http://www.gnu.org/copyleft/gpl.html (GPL) GENERAL PUBLIC LICENSE
 *
 * @see http://www.claroline.net/wiki/index.php/Config
 *
 * @author Claro Team <cvs@claroline.net>
 *
 * @package CLUSR
 *
 */
// TOOL
$conf_def['config_code'] = 'CLQWZ';
$conf_def['config_file'] = 'CLQWZ.conf.php';
$conf_def['config_name'] = 'Exercises';
$conf_def['config_class']='tool';


//SECTION
$conf_def['section']['main']['label']='Main settings';
//$conf_def['section']['main']['description']='';
$conf_def['section']['main']['properties'] =
array ( 'enableExerciseExportQTI'
       ,'exercisesPerPage',
       'showAllFeedbacks'
);

//PROPERTIES

$conf_def_property_list['enableExerciseExportQTI'] =
array ('label'         => 'Enable IMS-QTI Export'
      ,'description'   => ''
      ,'default'       => TRUE
      ,'type'          => 'boolean'
      ,'acceptedValue' => array ('TRUE'  => 'Yes'
                                ,'FALSE' => 'No'
                                )
      );

$conf_def_property_list['exercisesPerPage'] =
array ( 'label'   => 'Number of exercises per page'
      , 'default' => '25'
      , 'unit'    => 'exercices'
      , 'type'    => 'integer'
      , 'acceptedValue' => array ('min'=>'5')
      );

$conf_def_property_list['showAllFeedbacks'] =
array ('label'     => 'Display all feedbacks'
        ,'description' => 'If activated, all the feedbacks will be shown to users; if not, only those corresponding to the selected or correct answers will be displayed'
        ,'default'   => FALSE
        ,'type'      => 'boolean'
        ,'display'       => TRUE
        ,'readonly'      => FALSE
        ,'acceptedValue' => array ( 'TRUE'=> 'Yes', 'FALSE'=>'No' )
        );

?>
