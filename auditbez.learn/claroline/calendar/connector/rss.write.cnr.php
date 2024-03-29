<?php // $Id: rss.write.cnr.php 9706 2007-12-12 13:30:11Z mlaurent $
if ( count( get_included_files() ) == 1 ) die( '---' );
/**
 * CLAROLINE
 *
 * @version 1.8 $Revision: 9706 $
 *
 * @copyright (c) 2001-2006 Universite catholique de Louvain (UCL)
 *
 * @license http://www.gnu.org/copyleft/gpl.html (GPL) GENERAL PUBLIC LICENSE
 *
 * @package CLCAL
 * @package CLRSS
 *
 * @author Claro Team <cvs@claroline.net>
 */

function CLCAL_write_rss($context)
{

    if (is_array($context) && count($context)>0)
    {
        $courseId = (array_key_exists(CLARO_CONTEXT_COURSE,$context)) ? $context[CLARO_CONTEXT_COURSE] : claro_get_current_course_id();
    }

    require_once dirname(__FILE__) . '/../lib/agenda.lib.php';
    $eventList    = agenda_get_item_list($context, 'ASC');
    $toolNameList = claro_get_tool_name_list();

    $itemRssList = array();
    foreach ($eventList as $item)
    {
        if('SHOW' == $item['visibility'] )
        {
            $item['timestamp'] = strtotime($item['day'] . ' ' . $item['hour'] );
            $item['pubDate'] = date('r', $item['timestamp']);
            $item['dc:date'] = date('c', $item['timestamp']);

            //prepare values
            //c ISO 8601 date (added in PHP 5) 2004-02-12T15:19:21+00:00
            $item['dc:date'] = ('c' == $item['dc:date'])?date('Y-m-d\TH:i:sO', $item['timestamp']):$item['dc:date'];

            $itemRssList[] = array( 'title'       => $item['title']
            ,                       'category'    => trim($toolNameList['CLCAL'])
            ,                        'guid'        => get_path('rootWeb') .'claroline/' . 'calendar/agenda.php?cidReq=' . $courseId . '&amp;l#event' . $item['id']
            ,                        'link'        => get_path('rootWeb') .'claroline/' . 'calendar/agenda.php?cidReq=' . $courseId . '&amp;l#event' . $item['id']
            ,                        'description' => trim(str_replace('<!-- content: html -->','',$item['content']))
            ,                        'pubDate'     => $item['pubDate']
            ,                        'dc:date'     => $item['dc:date']
        //, 'author' => $_course['email']
            );
        }
    }

    return $itemRssList;
}

?>