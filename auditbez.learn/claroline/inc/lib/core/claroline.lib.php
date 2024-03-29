<?php // $Id: claroline.lib.php 12308 2010-04-29 12:59:05Z zefredz $

// vim: expandtab sw=4 ts=4 sts=4:

/**
 * Singleton class to represent the Claroline platform. This is a utility
 * class providing classes and methods to deal with the kernel and the page
 * display
 *
 * @version     1.9 $Revision: 12308 $
 * @copyright   2001-2010 Universite catholique de Louvain (UCL)
 * @author      Claroline Team <info@claroline.net>
 * @author      Frederic Minne <zefredz@claroline.net>
 * @license     http://www.gnu.org/copyleft/gpl.html
 *              GNU GENERAL PUBLIC LICENSE version 2 or later
 * @package     kernel.core
 */

require_once dirname(__FILE__) . '/../core/debug.lib.php';
require_once dirname(__FILE__) . '/../core/console.lib.php';
require_once dirname(__FILE__) . '/../core/event.lib.php';
require_once dirname(__FILE__) . '/../core/log.lib.php';
require_once dirname(__FILE__) . '/../core/url.lib.php';
require_once dirname(__FILE__) . '/../core/notify.lib.php';
require_once dirname(__FILE__) . '/../display/display.lib.php';
require_once dirname(__FILE__) . '/../database/database.lib.php';
require_once dirname(__FILE__) . '/../utils/ajax.lib.php';

/**
 * @deprecated since 1.9 use Claroline::PAGE instead
 */
define ( 'CL_PAGE',     'CL_PAGE' );
/**
 * @deprecated since 1.9 use Claroline::FRAMESET instead
 */
define ( 'CL_FRAMESET', 'CL_FRAMESET' );
/**
 * @deprecated since 1.9 use Claroline::POPUP instead
 */
define ( 'CL_POPUP',    'CL_POPUP' );
/**
 * @deprecated since 1.9 use Claroline::FRAME instead
 */
define ( 'CL_FRAME',    'CL_FRAME' );

/**
 * Main Claroline class containing references to Claroline kernel objects
 * This class is a Singleton
 */
class Claroline
{
    // Display type constants
    const PAGE      = 'CL_PAGE';
    const FRAMESET  = 'CL_FRAMESET';
    const POPUP     = 'CL_POPUP';
    const FRAME     = 'CL_FRAME';
    
    // Kernel objects
    /**
     * @var EventManager
     */
    public $eventManager;
    /**
     * @var ClaroNotification
     */
    public $notification;
    /**
     * @var ClaroNotifier
     */
    public $notifier;
    // Display object
    public $display;
    /**
     * @var Logger
     */
    public $logger;
    
    protected $moduleLabelStack;
    
    // this class is a singleton, use static method getInstance()
    private function __construct()
    {
        try
        {
            // initialize the event manager and notification classes
            $this->eventManager = EventManager::getInstance();
            $this->notification = ClaroNotification::getInstance();
            $this->notifier = ClaroNotifier::getInstance();
            
            // initialize logger
            $this->logger = new Logger();
            
            $this->moduleLabelStack = array();
            
            if ( isset($GLOBALS['tlabelReq']) )
            {
                $this->pushModuleLabel($GLOBALS['tlabelReq']);
                
                pushClaroMessage("Set current module to {$GLOBALS['tlabelReq']}", 'debug');
            }
        }
        catch ( Exception $e )
        {
            die( $e );
        }
    }
    
    /**
     * Add a label at the top of the module stack
     * @param string $label
     */
    public function pushModuleLabel( $label )
    {
        array_push( $this->moduleLabelStack, $label );
    }
    
    /**
     * Remove the modulke label at the top of the module stack
     */
    public function popModuleLabel()
    {
        array_pop( $this->moduleLabelStack );
    }
    
    /**
     * Get the label of the current module
     * @return string or false
     */
    public function currentModuleLabel()
    {
        if ( empty( $this->moduleLabelStack ) )
        {
            return false;
        }
        else
        {
           return $this->moduleLabelStack[count($this->moduleLabelStack)-1];
        }
    }
    
    /**
     * Set the type of display to use
     * @param   string type, display type could be
     *  Claroline::PAGE         a standard page with header, banner, body and footer
     *  Claroline::FRAMESET     a frameset with header and frames
     *  Claroline::POPUP        a popup-embedded page
     *  Claroline::FRAME        a frame-embedded page
     *  default value : Claroline::PAGE
     */
    public function setDisplayType( $type = self::PAGE )
    {
        switch ( $type )
        {
            case self::PAGE:
                $this->display = new ClaroPage;
                break;
            case self::POPUP:
                $this->display = new ClaroPage;
                $this->display->popupMode();
                break;
            case self::FRAME:
                $this->display = new ClaroPage;
                $this->display->frameMode();
                break;
            case self::FRAMESET:
                $this->display = new ClaroFramesetPage;
                break;
            default:
                throw new Exception( 'Invalid display type' );
        }
        
        JavascriptLoader::getInstance()->load('jquery');
        JavascriptLoader::getInstance()->load('claroline');
    }
    
    // Singleton instance
    private static $instance = false; // this class is a singleton
    
    /**
     * Returns the singleton instance of the Claroline object
     * @return  Claroline singleton instance
     */
    public static function getInstance()
    {
        if ( ! self::$instance )
        {
            self::$instance = new self;
        }

        return self::$instance;
    }
    
    /**
     * Get the current display object
     * @return Display which can be a ClaroPage or ClaroFramesetPage according
     *  to the display type
     */
    public static function getDisplay()
    {
        return self::getInstance()->display;
    }

    /**
     * Helper to initialize the display
     * @param string $displayType
     */
    public static function initDisplay( $displayType = self::PAGE )
    {
        self::getInstance()->setDisplayType( $displayType );
    }
    
    /**
     * Helper to log a message
     * @param string $type
     * @param string $data
     */
    public static function log( $type, $data )
    {
        self::getInstance()->logger->log($type, $data);
    }
    
    protected static $db = false;
    // Database link
    protected static $database = false;
    
    /**
     * Get the current database connection object
     * @return Claroline_Database_Connection
     */
    public static function getDatabase()
    {
        if ( ! self::$database )
        {
            // self::initMainDatabase();
            self::$database = new Claroline_Database_Connection(self::$db);
            self::$database->connect();
        }
        
        return self::$database;
    }
    
    public static function initMainDatabase()
    {
        if ( self::$db )
        {
            return;
        }
        
        if ( ! defined('CLIENT_FOUND_ROWS') ) define('CLIENT_FOUND_ROWS', 2);
        // NOTE. For some reasons, this flag is not always defined in PHP.
        
        self::$db = @mysql_connect(
            get_conf('dbHost'),
            get_conf('dbLogin'),
            get_conf('dbPass'),
            false,
            CLIENT_FOUND_ROWS );
        
        if ( ! self::$db )
        {
            throw new Exception ( 'FATAL ERROR ! SYSTEM UNABLE TO CONNECT TO THE DATABASE SERVER.' );
        }
        
        // NOTE. CLIENT_FOUND_ROWS is required to make claro_sql_query_affected_rows()
        // work properly. When using UPDATE, MySQL will not update columns where the new
        // value is the same as the old value. This creates the possiblity that
        // mysql_affected_rows() may not actually equal the number of rows matched,
        // only the number of rows that were literally affected by the query.
        // But this behavior can be changed by setting the CLIENT_FOUND_ROWS flag in
        // mysql_connect(). mysql_affected_rows() will return then the number of rows
        // matched, even if none are updated.
        
        $selectResult = mysql_select_db( get_conf('mainDbName'), self::$db);
        
        if ( ! $selectResult )
        {
            throw new Exception ( 'FATAL ERROR ! SYSTEM UNABLE TO SELECT THE MAIN CLAROLINE DATABASE.' );
        }

        if ($GLOBALS['statsDbName'] == '')
        {
            $GLOBALS['statsDbName'] = get_conf('mainDbName');
        }
    }
    
    protected static $_ajaxServiceBroker = false;
        
    /**
     * Get kernel Ajax Service Broker instance
     * @return Ajax_Remote_Service_Broker
     * @since Claroline 1.9.5
     */
    public static function ajaxServiceBroker()
    {
        if ( ! self::$_ajaxServiceBroker )
        {
            self::$_ajaxServiceBroker = new Ajax_Remote_Service_Broker();
        }
        
        return self::$_ajaxServiceBroker;
    }
}
