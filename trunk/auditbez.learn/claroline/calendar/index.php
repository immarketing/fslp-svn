<?php // $Id: index.php 9756 2008-01-23 14:15:58Z gregk84 $
/**
 * CLAROLINE
 *
 * select the good agenda waiting that two scripts are merged.
 *
 * @version 1.8 $Revision: 9756 $
 *
 * @copyright (c) 2001-2007 Universite catholique de Louvain (UCL)
 *
 * @license http://www.gnu.org/copyleft/gpl.html (GPL) GENERAL PUBLIC LICENSE
 *
 * @package CLCAL
 *
 * @author Claro Team <cvs@claroline.net>
 */

$tlabelReq = 'CLCAL';

require_once dirname(__FILE__) . '/../../claroline/inc/claro_init_global.inc.php';

if ( claro_is_in_a_course() )
{
    claro_redirect('./agenda.php');
}
else
{
    claro_redirect('./myagenda.php');
}
exit();

?>