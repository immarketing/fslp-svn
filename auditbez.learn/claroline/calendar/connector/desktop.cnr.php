<?php // $Id: desktop.cnr.php 10725 2008-07-22 11:55:30Z zefredz $

// vim: expandtab sw=4 ts=4 sts=4:

if ( count( get_included_files() ) == 1 )
{
    die( 'The file ' . basename(__FILE__) . ' cannot be accessed directly, use include instead' );
}

/**
* CLAROLINE
*
* User desktop : MyCalendar portlet
* FIXME : move to calendar module
*
* @version      1.9 $Revision: 10725 $
* @copyright    (c) 2001-2008 Universite catholique de Louvain (UCL)
* @license      http://www.gnu.org/copyleft/gpl.html (GPL) GENERAL PUBLIC LICENSE
* @package      DESKTOP
* @author       Claroline team <info@claroline.net>
*
*/

require_once get_module_path( 'CLCAL' ) . '/lib/agenda.lib.php';

class CLCAL_Portlet extends UserDesktopPortlet
{
    public function renderContent()
    {
        $output = '<div id="portletMycalendar">' . "\n"
            . '<img src="'.get_icon_url('loading').'" alt="" />' . "\n"
            . '</div>' . "\n"
            . '<div style="clear:both;"></div>' . "\n"
            ;
        
        $output .= "<script type=\"text/javascript\">
$(document).ready( function(){
    $('#portletMycalendar').load('"
        .get_module_url('CLCAL')."/ajaxHandler.php');
});
</script>";

        return $output;
    }

    public function renderTitle()
    {
        return get_lang('My calendar');
    }
}
