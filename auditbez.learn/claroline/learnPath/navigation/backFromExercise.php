<?php // $Id: backFromExercise.php 9706 2007-12-12 13:30:11Z mlaurent $
/**
 * CLAROLINE 
 *
 * @version 1.8 $Revision: 9706 $
 *
 * @copyright (c) 2001-2006 Universite catholique de Louvain (UCL)
 *
 * @license http://www.gnu.org/copyleft/gpl.html (GPL) GENERAL PUBLIC LICENSE
 *
 * @author Piraux Sébastien <pir@cerdecam.be>
 * @author Lederer Guillaume <led@cerdecam.be>
 *
 * @package CLLNP
 *
 */
require '../../inc/claro_init_global.inc.php';
?>
<html>
<head>
 <script>
  <!-- //
   parent.tocFrame.location.href="tableOfContent.php";
  //-->
 </script> 
</head>
<body>
 <center>
  <br /><br /><br />
  <p>
<?php
if($_GET['op'] == 'cancel')
{
    echo get_lang('Exercise cancelled, choose a module in the list to continue.');
}
elseif($_GET['op'] == 'finish') // exercise done
{
    echo get_lang('Exercise done, choose a module in the list to continue.');
}
?>
   </p>
  </center>
 </body>
</html>