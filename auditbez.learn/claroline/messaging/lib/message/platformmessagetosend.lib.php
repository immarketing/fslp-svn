<?php // $Id: platformmessagetosend.lib.php 12503 2010-07-15 14:14:43Z ffervaille $

// vim: expandtab sw=4 ts=4 sts=4:

/**
 * message from platform to send class
 *
 * @version     1.9 $Revision: 12503 $
 * @copyright   2001-2008 Universite catholique de Louvain (UCL)
 * @author      Claroline Team <info@claroline.net>
 * @author      Frederic Fervaille <frederic.fervaille@uclouvain.be>
 * @license     http://www.gnu.org/copyleft/gpl.html
 *              GNU GENERAL PUBLIC LICENSE version 2 or later
 * @package     internal_messaging
 */

require_once dirname(__FILE__) . '/messagetosend.lib.php';

class PlatformMessageToSend extends MessageToSend
{
    const CLARO_SYSTEM_USER_ID = 0;
    
    /**
     * create an message to send with the information in parameters
     *
     * @param int $sender user identification
     *         if it's not defined it use the current user id
     * @param string $subject subject of the message
     * @param string $message content of the message
     */
    public function __construct( $subject = parent::NOSUBJECT, 
        $message = parent::NOMESSAGE )
    {
        parent::__construct( self::CLARO_SYSTEM_USER_ID, $subject , $message );
    }
    
    /**
     * The sender cannot be changed
     * @see MessageToSend::setSender
     * @throws Exception when called
     */
    public function setSender( $userId )
    {
        throw new Exception( 'Sender cannot be changed!' );
    }
}