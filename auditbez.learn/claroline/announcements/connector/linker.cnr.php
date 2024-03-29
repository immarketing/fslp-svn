<?php // $Id: linker.cnr.php 11109 2008-09-02 14:12:45Z zefredz $

// vim: expandtab sw=4 ts=4 sts=4:

/**
 * Resource Resolver for the Annoucements tool
 *
 * @version 1.9 $Revision: 11109 $
 * @copyright (c) 2001-2008 Universite catholique de Louvain (UCL)
 * @license http://www.gnu.org/copyleft/gpl.html (GPL) GENERAL PUBLIC LICENSE
 * @author claroline Team <cvs@claroline.net>
 * @package CLANN
 *
 */

FromKernel::uses('fileManage.lib', 'file.lib');

class CLANN_Resolver implements ModuleResourceResolver
{
    public function resolve ( ResourceLocator $locator )
    {
        if ( $locator->hasResourceId() )
        {
            return get_module_entry_url('CLANN') . "#ann{$locator->getResourceId()}";
        }
        else
        {
            return get_module_entry_url('CLANN');
        }
    }

    public function getResourceName( ResourceLocator $locator)
    {
        if ( ! $locator->hasResourceId() )
        {
            return false;
        }
        
        $tbl = get_module_course_tbl( array('announcement'), $locator->getCourseId() );
        
        $sql = "SELECT `title`\n"
            . "FROM `{$tbl['announcement']}`\n"
            . "WHERE `id`=". Claroline::getDatabase()->escape( $locator->getResourceId() )
            ;
        
        $res = Claroline::getDatabase()->query($sql);
        $res->setFetchMode(Database_ResultSet::FETCH_VALUE);
        $title = trim( $res->fetch() );
        
        if ( empty( $title ) )
        {
            $title = get_lang('Untitled');
        }
        
        return $title;
    }
}

class CLANN_Navigator implements ModuleResourceNavigator
{
    public function getResourceId( $params = array() )
    {
        if ( isset( $params['id'] ) )
        {
            return $params['id'];
        }
        else
        {
            return false;
        }
    }
    
    public function isNavigable( ResourceLocator $locator )
    {
        if (  $locator->hasResourceId() )
        {
            return false;
        }
        else
        {
            return $locator->inModule() && $locator->getModuleLabel() == 'CLANN';
        }
    }
    
    public function getParentResourceId( ResourceLocator $locator )
    {
        return false;
    }
    
    public function getResourceList( ResourceLocator $locator )
    {
        $tbl = get_module_course_tbl( array('announcement'), $locator->getCourseId() );
        
        $sql = "SELECT `id`, `title`, `visibility`\n"
            . "FROM `{$tbl['announcement']}`"
            ;
        
        $res = Claroline::getDatabase()->query($sql);
        
        $resourceList = new LinkerResourceIterator;
        
        foreach ( $res as $annoucement )
        {
            $annoucementLoc = new ClarolineResourceLocator(
                $locator->getCourseId(),
                'CLANN',
                (int) $annoucement['id']
            );
            
            $annoucementResource = new LinkerResource(
                ( empty( $annoucement['title'] )
                    ? get_lang('Untitled')
                    : $annoucement['title'] ),
                $annoucementLoc,
                true,
                ( $annoucement['visibility'] == 'HIDE'
                    ? false
                    : true ),
                false
            );
            
            $resourceList->addResource( $annoucementResource );
        }
        
        return $resourceList;
    }
}
