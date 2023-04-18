<?php

namespace Database\Seeders;

use Illuminate\Support\Str;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class GameSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run(){

        DB::table('games')->insert([
            [
                'name' => 'League of Legends',
                'slug' => 'game/'.Str::slug('League of Legends'),
                'description' => 'League of Legends is a team-based game with over 140 champions to make epic plays with. Play now for free.',
                'rating' => 4.5,
                'rating_count' => 100,
                'status' => 'Active',
                'is_deleted' => 0,
            ],
            [
                'name' => 'Counter-Strike: Global Offensive',
                'slug' => 'game/'.Str::slug('Counter-Strike: Global Offensive'),
                'description' => 'Counter-Strike: Global Offensive (CS: GO) will expand upon the team-based action gameplay that it pioneered when it was launched 19 years ago. CS: GO features new maps, characters, and weapons and delivers updated versions of the classic CS content (de_dust, etc.).',
                'rating' => 4.5,
                'rating_count' => 100,
                'status' => 'Active',
                'is_deleted' => 0,
            ],
            [
                'name' => 'Dota 2',
                'slug' => 'game/'.Str::slug('Dota 2'),
                'description' => 'Dota 2 is a multiplayer online battle arena (MOBA) video game in which two teams of five players compete to collectively destroy a large structure defended by the opposing team known as the "Ancient", whilst defending their own.',
                'rating' => 4.5,
                'rating_count' => 100,
                'status' => 'Active',
                'is_deleted' => 0,
            ],
            [
                'name' => 'Apex Legends',
                'slug' => 'game/'.Str::slug('Apex Legends'),
                'description' => 'Apex Legends is a free-to-play battle royale game where legendary competitors battle for glory, fame, and fortune on the fringes of the Frontier. Master an ever-growing roster of diverse Legends, deep tactical squad play, and bold new innovations that level up the battle royale experience—all within a rugged world where anything goes. Welcome to the next evolution of battle royale.',
                'rating' => 4.5,
                'rating_count' => 100,
                'status' => 'Active',
                'is_deleted' => 0,
            ],
            [
                'name' => 'Fortnite',
                'slug' => 'game/'.Str::slug('Fortnite'),
                'description' => 'Fortnite is the completely free multiplayer game where you and your friends collaborate to create your dream Fortnite world or battle to be the last one standing. Play both Battle Royale and Fortnite Creative for FREE.',
                'rating' => 4.5,
                'rating_count' => 100,
                'status' => 'Active',
                'is_deleted' => 0,
            ]
        ]);
    }
}
