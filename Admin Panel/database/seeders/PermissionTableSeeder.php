<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;

class PermissionTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        // Reset cached roles and permissions
        // app()[PermissionRegistrar::class]->forgetCachedPermissions();

        $permissions = config('backend.permission');
        foreach ($permissions as $key => $permission) {
            foreach ($permission as $key2 => $value) {
                \Spatie\Permission\Models\Permission::create(['name' => $value]);
            }
        }

        // assign all permissions to config role first index
        foreach(config('backend.role') as $key => $role){
            if($key == 0){
                $role = \Spatie\Permission\Models\Role::findByName($role);
                $role->givePermissionTo(\Spatie\Permission\Models\Permission::all());
            }
        }

        // assign role to user
        $user = \App\Models\User::first();
        $user->assignRole(config('backend.role')[0]);

    }
}
