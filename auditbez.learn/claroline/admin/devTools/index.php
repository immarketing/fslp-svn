<?php // $Id: index.php 11767 2009-05-18 10:46:58Z dimitrirambout $
/**
 * CLAROLINE
 *
 * This is the index page of sdk tools
 *
 * @version 1.9 $Revision: 11767 $
 *
 * @copyright (c) 2001-2009 Universite catholique de Louvain (UCL)
 *
 * @license http://www.gnu.org/copyleft/gpl.html (GPL) GENERAL PUBLIC LICENSE
 *
 * @package SDK
 *
 * @author Claro Team <cvs@claroline.net>
 * @author Christophe Gesch� <moosh@claroline.net>
 * @author Dimitri Rambout <dimitri.rambout@uclouvain.be>
 *
 */

require '../../inc/claro_init_global.inc.php';
if (file_exists(get_path('rootSys') .'platform/currentVersion.inc.php')) include (get_path('rootSys') . 'platform/currentVersion.inc.php');

$is_allowedToUseSDK = claro_is_platform_admin();

if (! $is_allowedToUseSDK) claro_disp_auth_form();

if ( get_conf('DEVEL_MODE',false))
{
    $devtoolsList = array();
    if (file_exists('./fillUser.php'))        $devtoolsList[] = claro_html_tool_link('fillUser.php',  get_lang('Create fake users'));
    if (file_exists('./fillCourses.php'))     $devtoolsList[] = claro_html_tool_link('fillCourses.php',  get_lang('Create fake courses'));
    if (file_exists('./fillTree.php'))        $devtoolsList[] = claro_html_tool_link('fillTree.php',  get_lang('Create fake categories'));
    if (file_exists('./fillToolCourses.php')) $devtoolsList[] = claro_html_tool_link('fillToolCourses.php',  get_lang('Create item into courses tools'));
}

$nameTools = get_lang('Devel Tools');

ClaroBreadCrumbs::getInstance()->prepend( get_lang('Administration'), get_path('rootAdminWeb') );

$out = '';

$out .= claro_html_tool_title($nameTools);

// TODO use claro_disp_title
$out .= '<h4>' . get_lang('Translations') . '</h4>
    <ul>
        <li><a href="../xtra/sdk/translation_index.php">' . get_lang('Translations') . '</a></li>
    </ul>'
    ;

if ( 0 < count($devtoolsList))
{
    $out .=  claro_html_tool_title(get_lang('Filling'))
    .     claro_html_menu_vertical($devtoolsList)
    ;
}

$claroline->display->body->appendContent($out);

echo $claroline->display->render();

?>