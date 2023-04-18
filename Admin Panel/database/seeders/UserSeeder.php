<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;

class UserSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {

        DB::table('users')->insert([
            [
                'id' => 1,
                'full_name' => 'Al Mahmud Riaz',
                'email' => 'riaz@demo.com',
                'password' => Hash::make('12345678')
            ],
            [
                'id' => 2,
                'full_name' => 'Demo User',
                'email' => 'demo@demo.com',
                'password' => Hash::make('12345678')
            ],
            [
                'id' => 3,
                'full_name' => 'Admin User',
                'email' => 'admin@demo.com',
                'password' => Hash::make('12345678')
            ],
        ]);

        DB::table('profiles')->insert([
            [
                'id' => 1,
                'user_id' => 1,
                'first_name' => 'Al Mahmud',
                'last_name' => 'Riaz',
                'username' => 'riaz',
                'email' => 'riaz@demo.com',
                'status' => 'Active',
                'is_deleted' => 0,
            ],
            [
                'id' => 2,
                'user_id' => 2,
                'first_name' => 'Demo',
                'last_name' => 'User',
                'username' => 'demo',
                'email' => 'demo@demo.com',
                'status' => 'Active',
                'is_deleted' => 0,
            ],
            [
                'id' => 3,
                'user_id' => 3,
                'first_name' => 'Admin',
                'last_name' => 'User',
                'username' => 'admin',
                'email' => 'admin@demo.com',
                'status' => 'Active',
                'is_deleted' => 0,
            ],
        ]);

    }
}
