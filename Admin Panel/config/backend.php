<?php


return[

    /*
    |--------------------------------------------------------------------------
    | Site Settings
    |--------------------------------------------------------------------------
    | This is the site settings. You can change the site name, description, and
    | other settings here.
    |
    */
    'site' =>[
        'title' => 'Video Game Review',
        'short_title' => 'VGR',
        'keywords' => 'Video Game Review, VGR, Video Game, Review',
        'description' => 'Video Game Review is a website where you can find the latest video game reviews.',
        'favicon' => 'assets/images/favicon/favicon.ico',
        'author' => 'Abdul Al Mahmud Riaz',
        'logo' => 'assets/images/logo/logo.png',
        'email' => 'me@almahmudriaz.com',
        'phone' => '+8807xxxxxxxxx',
        'address'=> 'United Kingdom',
    ],

    /*
    |--------------------------------------------------------------------------
    | Temporary Folder Settings
    |--------------------------------------------------------------------------
    | This is the temporary folder settings. You can change the temporary folder name here.
    |
    */
    'temp_folder' => [
        'lecture' => [
            'session_key' => 'lecture_folder',
            'path' => 'public/uploads/temp/lecture/',
            'upload_storage_path' => 'uploads/temp/lecture/',
            'storage_path' => 'storage/uploads/temp/lecture/',
        ],
    ],

    /*
    |--------------------------------------------------------------------------
    | Role Settings
    |--------------------------------------------------------------------------
    |
    | Here all the roles are defined. That will be used for assigning the roles to the users.
    | You can add more roles here. But make sure you add the same role in the database. Otherwise it will not work.
    | You can add the role in the database by running the command: php artisan db:seed --class=RoleTableSeeder
    */
    'role' => [
        'Super Admin',
        'Admin',
        'User',
    ],

    /*
    |--------------------------------------------------------------------------
    | Permission Settings
    |--------------------------------------------------------------------------
    |
    | Here all the permissions are defined. That will be used for assigning the permissions to the roles.
    | You can add more permissions here. But make sure you add the same permission in the database. Otherwise it will not work.
    | You can add the permission in the database by running the command: php artisan db:seed --class=PermissionTableSeeder
    |
    */
    'permission' => [
        'Admin Dashboard' => [
            'show' => 'admin.dashboard.index',
        ],
        'User' => [
            'Show' => 'admin.user.index',
            'Delete' => 'admin.user.delete',
            'Assign Role' => 'admin.user.assign.role',
        ],
        'Role Permission' => [
            'Show' => 'admin.role.index',
            'Create' => 'admin.role.create',
            'Update' => 'admin.role.edit',
            'Delete' => 'admin.role.delete',
            'Assign Permission' => 'admin.role.assign.permission',
        ],
    ],


    /*
    |--------------------------------------------------------------------------
    | Course Settings
    |--------------------------------------------------------------------------
    |
    | Here all the course settings are defined. That will be used for creating the course.
    |
    */
    'games' => [
        'image_sizes' =>[
            '_thumbnail' => [
                'width' => 100,
                'height' => 100,
            ],
            '_small' => [
                'width' => 300,
                'height' => 200,
            ],
            '_medium' => [
                'width' => 800,
                'height' => 600,
            ],
            '_large' => [
                'width' => 1500,
                'height' => 700,
            ],
            '_extra-large' => [
                'width' => 2440,
                'height' => 1578,
            ]
        ],
    ],

];
