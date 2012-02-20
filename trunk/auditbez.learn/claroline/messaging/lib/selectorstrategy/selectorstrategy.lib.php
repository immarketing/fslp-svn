<?php
 // $Id: selectorstrategy.lib.php 10009 2008-05-07 14:39:57Z thetotof $

// vim: expandtab sw=4 ts=4 sts=4:

/**
 * selector strategy interface
 *
 * @version     1.9 $Revision: 10009 $
 * @copyright   2001-2008 Universite catholique de Louvain (UCL)
 * @author      Claroline Team <info@claroline.net>
 * @author      Christophe Mertens <thetotof@gmail.com>
 * @license     http://www.gnu.org/copyleft/gpl.html
 *              GNU GENERAL PUBLIC LICENSE version 2 or later
 * @package     internal_messaging
 */

interface SelectorStrategy
{
    /**
     * return the where clause of the sql request
     * @return string
     */
    public function getStrategy();
    
    /**
     * return string the limit clause of the sql request
     * @return string
     */
    public function getLimit();
    
    /**
     * return the order clause of the request
     * @return string
     */
    public function getOrder();
}
