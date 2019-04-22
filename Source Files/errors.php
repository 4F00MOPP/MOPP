<?php
if (count($errorArray) > 0): ?>
    <?php foreach ($errorArray as $error): ?>
        <p><?php echo $error; ?></p>
    <?php endforeach ?>
<?php endif ?>