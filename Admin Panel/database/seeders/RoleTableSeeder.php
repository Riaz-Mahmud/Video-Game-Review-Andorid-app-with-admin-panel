<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;

class RoleTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $roles = config('backend.role');
        foreach ($roles as $key => $role) {
            \Spatie\Permission\Models\Role::create(['name' => $role]);
        }
    }
}
