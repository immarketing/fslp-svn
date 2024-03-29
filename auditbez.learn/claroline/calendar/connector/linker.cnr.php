<?php // $Id: linker.cnr.php 11107 2008-09-02 12:46:50Z zefredz $

// vim: expandtab sw=4 ts=4 sts=4:

/**
 * Resource Resolver for the Calendar tool
 *
 * @version 1.9 $Revision: 11107 $
 * @copyright (c) 2001-2008 Universite catholique de Louvain (UCL)
 * @license http://www.gnu.org/copyleft/gpl.html (GPL) GENERAL PUBLIC LICENSE
 * @author claroline Team <cvs@claroline.net>
 * @package CLCAL
 *
 */

FromKernel::uses('fileManage.lib', 'file.lib');

class CLCAL_Resolver implements ModuleResourceResolver
{
    public function resolve ( ResourceLocator $locator )
    {
        if ( $locator->hasResourceId() )
        {
            return get_module_entry_url('CLCAL') . "#event{$locator->getResourceId()}";
        }
        else
        {
            return get_module_entry_url('CLCAL');
        }
    }

    public function getResourceName( ResourceLocator $locator )
    {
        if ( ! $locator->hasResourceId() )
        {
            return false;
        }
        
        $tbl = get_module_course_tbl( array('calendar_event'), $locator->getCourseId() );
        
        $sql = "SELECT `titre`,`day`\n"
            . "FROM `{$tbl['calendar_event']}`\n"
            . "WHERE `id`=". Claroline::getDatabase()->escape( $locator->getResourceId() )
            ;
        
        $res = Claroline::getDatabase()->query($sql);
        $res->setFetchMode(Database_ResultSet::FETCH_OBJECT);
        $event = $res->fetch();
        
        $titre = trim( $event->titre );
        
        if ( empty( $titre ) )
        {
            $titre = $event->day;
        }
        
        return $titre;
    }
}

class CLCAL_Navigator implements ModuleResourceNavigator
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
            return $locator->inModule() && $locator->getModuleLabel() == 'CLCAL';
        }
    }
    
    public function getParentResourceId( ResourceLocator $locator )
    {
        return false;
    }
    
    public function getResourceList( ResourceLocator $locator )
    {
        $tbl = get_module_course_tbl( array('calendar_event'), $locator->getCourseId() );
        
        $sql = "SELECT `id`, `titre`, `day`, `visibility`\n"
            . "FROM `{$tbl['calendar_event']}`"
            ;
        
        $res = Claroline::getDatabase()->query($sql);
        
        $resourceList = new LinkerResourceIterator;
        
        foreach ( $res as $event )
        {
            $eventLoc = new ClarolineResourceLocator(
                $locator->getCourseId(),
                'CLCAL',
                (int) $event['id']
            );
            
            $eventResource = new LinkerResource(
                ( empty( $event['titre'] )
                    ? $event['day']
                    : $event['titre'] ),
                $eventLoc,
                true,
                ( $event['visibility'] == 'HIDE'
                    ? false
                    : true ),
                false
            );
            
            $resourceList->addResource( $eventResource );
        }
        
        return $resourceList;
    }
}
