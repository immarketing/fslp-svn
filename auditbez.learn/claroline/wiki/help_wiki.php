<?php // $Id: help_wiki.php 11785 2009-05-25 14:35:27Z dimitrirambout $
    // vim: expandtab sw=4 ts=4 sts=4:
    
    /**
     * CLAROLINE
     *
     * @version 1.8 $Revision: 11785 $
     *
     * @copyright 2001-2006 Universite catholique de Louvain (UCL)
     *
     * @license http://www.gnu.org/copyleft/gpl.html (GPL) GENERAL PUBLIC LICENSE
     *
     * @author Frederic Minne <zefredz@gmail.com>
     *
     * @package Wiki
     */
    
    require '../inc/claro_init_global.inc.php';

    $nameTools = get_lang("Wiki");
    $hide_banner=TRUE;
    
    $htmlHeadXtra[] =
        '<style type="text/css">
            dt{font-weight:bold;margin-top:5px;}
        </style>';
    
    $out = '';
    
    $help = ( isset( $_REQUEST['help'] ) ) ? $_REQUEST['help'] : 'syntax';
    
    //$out .= '<center><a href="#" onclick="window.close()">'.get_lang("Close window").'</a></center>' . "\n";
    
    switch( $help )
    {
        case 'syntax':
        {
            $out .= get_block('blockWikiHelpSyntaxContent');
            break;
        }
        case 'admin':
        {
            $out .= get_block('blockWikiHelpAdminContent');
            break;
        }
        default:
        {
            $out .= '<center><h1>'.get_lang('Wrong parameters').'</h1></center>';
        }
    }
    
    //$out .= '<center><a href="#" onclick="window.close()">'.get_lang("Close window").'</a></center>' . "\n";
    
    $hide_footer = true;
    
    $claroline->setDisplayType(Claroline::POPUP);
    $claroline->display->body->appendContent($out);

    echo $claroline->display->render();
    
?>