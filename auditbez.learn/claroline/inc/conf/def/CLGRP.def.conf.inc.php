<?php // $Id: CLGRP.def.conf.inc.php 9706 2007-12-12 13:30:11Z mlaurent $
if ( count( get_included_files() ) == 1 ) die( '---' );
/**
 * CLAROLINE
 *
 * This file describe the parameter for edit setting  of groups in a course
 *
 * @version 1.8 $Revision: 9706 $
 *
 * @copyright 2001-2006 Universite catholique de Louvain (UCL)
 *
 * @license http://www.gnu.org/copyleft/gpl.html (GPL) GENERAL PUBLIC LICENSE
 *
 * @see http://www.claroline.net/wiki/index.php/Config
 * @see http://www.claroline.net/wiki/index.php/CLGRP
 *
 * @author Claro Team <cvs@claroline.net>
 *
 * @package CLGRP
 *
 */
// TOOL

$conf_def['config_code']='CLGRP';
$conf_def['config_file']='CLGRP.conf.php';
$conf_def['config_name']='Groups permissions';
$conf_def['config_class']='Groups';


$conf_def['section']['users']['label']='Users';
$conf_def['section']['users']['description']='Settings for users of group';
$conf_def['section']['users']['properties'] =
array ( 'multiGroupAllowed'
      );


$conf_def['section']['tutors']['label']='Tutors';
$conf_def['section']['tutors']['description']='Settings for tutors of group';
$conf_def['section']['tutors']['properties'] =
array ( 'tutorCanBeSimpleMemberOfOthersGroupsAsStudent'
      , 'showTutorsInGroupList'
      );

//PROPERTIES
$conf_def_property_list['multiGroupAllowed'] =
array ( 'label'       => 'Allow teachers to subscribe a user in several groups'
      , 'description' => ''
      , 'default'     => TRUE
      , 'type'        => 'boolean'
      , 'display'     => TRUE
      , 'readonly'    => FALSE
      , 'acceptedValue' => array ( 'TRUE'=>'Yes'
                               , 'FALSE'=>'No')
      );

$conf_def_property_list['tutorCanBeSimpleMemberOfOthersGroupsAsStudent'] =
array ( 'label'       => 'Tutors can subscribe to a group as a simple member'
      , 'description' => 'A tutor attached to a group can subscribe himself to another group as a simple user.'
      , 'default'     => FALSE
      , 'type'        => 'boolean'
      , 'acceptedValue' => array ('TRUE'=>'Yes'
                               ,'FALSE'=>'No'
                               )
      , 'display'     => TRUE
      , 'readonly'    => FALSE
      );

$conf_def_property_list['showTutorsInGroupList'] =
array ( 'description' => 'Not implemented, name reserved  for future version of Claroline'
      , 'label'       => 'Whether include tutors in the displayed member list'
      , 'default'     => FALSE
      , 'type'        => 'boolean'
      , 'acceptedValue' => array ('TRUE'=>'Yes'
                               ,'FALSE'=>'No'
                               )
      , 'display'     => FALSE
      , 'readonly'    => TRUE
      );
?>