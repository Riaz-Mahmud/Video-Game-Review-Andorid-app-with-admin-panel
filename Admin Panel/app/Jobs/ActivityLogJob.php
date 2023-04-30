<?php

namespace App\Jobs;

use App\Models\ActivityLog;
use Jenssegers\Agent\Agent;
use Illuminate\Bus\Queueable;
use Illuminate\Queue\SerializesModels;
use Illuminate\Queue\InteractsWithQueue;
use Illuminate\Contracts\Queue\ShouldQueue;
use Illuminate\Foundation\Bus\Dispatchable;
use Illuminate\Contracts\Queue\ShouldBeUnique;
use Illuminate\Support\Facades\Log;
use Stevebauman\Location\Facades\Location;

class ActivityLogJob implements ShouldQueue
{
    use Dispatchable, InteractsWithQueue, Queueable, SerializesModels;

    protected $data;

    /**
     * Create a new job instance.
     *
     * @return void
     */
    public function __construct($data)
    {
        $this->data = $data;
    }

    /**
     * Execute the job.
     *
     * @return void
     */
    public function handle(){

        try{
            $data = array();
            $data['user_id'] = $this->data['user_id'] ?? null;
            $data['description'] = $this->data['description'] ?? null;
            $data['url'] = $this->data['url'] ?? null;
            $data['method'] = $this->data['method'] ?? null;
            $data['route'] = $this->data['route'] ?? null;

            $data['ip'] = $this->data['ip'] ?? null;

            $ipDetails = [];
            $locate = Location::get($this->data['ip']);
            if ($locate) {
                $ipDetails['lat'] = $locate->latitude;
                $ipDetails['lng'] = $locate->longitude;

                $data['city'] = $locate->cityName;
                $data['region'] = $locate->regionName;
                $data['country'] = $locate->countryName;
            } else {
                $ipDetails['lat'] = null;
                $ipDetails['lng'] = null;

                $data['city'] = null;
                $data['region'] = null;
                $data['country'] = null;
            }

            $data['ip_info'] = json_encode($ipDetails);
            $data['agent'] = $this->data['agent'] ?? null;
            $data['device_data'] = $this->BrowserDetails($this->data['device_data']);

            ActivityLog::insert($data);

        }catch(\Exception $e){

            Log::info('Activity Log job error: ' . $e->getMessage());
        }

    }

    protected function BrowserDetails($agent){
        try {
            $browser = $agent->browser();

            $details = array(
                'browser' => $browser,
                'version' => $agent->version($browser),
                'platform' => $agent->platform(),
                'device' => $agent->device(),
                'isRobot' => $agent->isRobot(),
                'robot' => $agent->robot(),
                'phone' => $agent->isPhone(),
                'mobile' => $agent->isMobile(),
                'androidOS' => $agent->isAndroidOS(),
                'nexus' => $agent->isNexus(),
                'safari' => $agent->isSafari(),
                'tablet' => $agent->isTablet(),
                'desktop' => $agent->isDesktop(),
                'laptop' => $agent->isLaptop(),
                'desktopDevice' => $agent->isDesktopDevice(),
                'mobileDevice' => $agent->isMobileDevice(),
                'desktopOrLaptop' => $agent->isDesktopOrLaptop(),
                'touchDevice' => $agent->isTouchDevice(),
                'crawler' => $agent->isCrawler(),
                'pim' => $agent->isPIM(),
            );
            return json_encode($details);

        } catch (\Exception $e) {
            return null;
        }
    }
}
