// $Id: Date.fr.js 11862 2009-08-12 08:37:24Z zefredz $
// vim: expandtab sw=4 ts=4 sts=4:

/** 
 * French locale for Date.js library by Henrik Lindqvist
 *
 * @version     1.0 $Revision: 11862 $
 * @copyright   2001-2009 Universite catholique de Louvain (UCL)
 * @author      Frederic Minne <zefredz@claroline.net>
 * @license      http://www.gnu.org/licenses/lgpl-3.0.txt
 *              GNU LESSER GENERAL PUBLIC LICENSE Version 3.0 or later
 * @package     core.js
 *
 */

(function (d) {

d.i18n['fr'] = 
d.i18n['fr-FR'] = {
  months: {
    abbr: [ 'Jan', 'Fév', 'Mar', 'Avr', 'Mai', 'Jun', 'Jul', 'Aou', 'Sep', 'Oct', 'Nov', 'Déc' ],
    full: [ 'Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre' ]
  },
  days: {
    abbr: [ 'Dim', 'Lun', 'Mar', 'Mer', 'Jeu', 'Ven', 'Sam' ],
    full: [ 'Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi' ]
  },
  week: {   // Used by date pickers
    abbr: 'Sm',
    full: 'Semaine'
  },
  ad: 'AD',
  am: 'AM',
  pm: 'PM',
  gmt: 'GMT',
  z: ':',   // Hour - minute separator
  Z: '',    // Hour - minute separator
  fdow: 1,  // First day of week
  mdifw: 1  // Minimum days in first week
};

})(Date);