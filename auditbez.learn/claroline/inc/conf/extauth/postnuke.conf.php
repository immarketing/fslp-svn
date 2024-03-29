<?php // $Id: postnuke.conf.php 11515 2009-01-16 11:36:27Z zefredz $

/**
 * Postnuke authentication driver
 *
 * @version     1.9 $Revision: 11515 $
 * @copyright   2001-2008 Universite catholique de Louvain (UCL)
 * @author      Claroline Team <info@claroline.net>
 * @license     http://www.gnu.org/copyleft/gpl.html
 *              GNU GENERAL PUBLIC LICENSE version 2 or later
 * @package     CLAUTH
 */

if ( count( get_included_files() ) == 1 )
{
    die( 'The file ' . basename(__FILE__) . ' cannot be accessed directly, use include instead' );
}

// do not change the following section
$driverConfig['driver'] = array(
    'enabled' => true,
    'class' => 'PearAuthDriver',
    'authSourceType' => 'DB',
    'authSourceName' => 'postnuke',
    'userRegistrationAllowed' => true,
    'userUpdateAllowed' => true
);

// you can change the driver from this point

$driverConfig['extAuthOptionList'] = array(
    'dsn'         => 'mysql://dbuser:dbpasswor@domain/postnuke',

    'table'       => 'nk_users', // warning ! table prefix can change from one system to another
    'usernamecol' => 'pn_uname',
    'passwordcol' => 'pn_pass',
    'db_fields'   => array('pn_name', 'pn_email'),
    'cryptType'   => 'md5'
);

$driverConfig['extAuthAttribNameList'] = array(
    'lastname'     => 'pn_name',
    'email'        => 'pn_email'
);

$driverConfig['extAuthAttribTreatmentList'] = array (
    'status' => 5
);

$driverConfig['extAuthAttribToIgnore'] = array(
    'isCourseCreator'
);
?>