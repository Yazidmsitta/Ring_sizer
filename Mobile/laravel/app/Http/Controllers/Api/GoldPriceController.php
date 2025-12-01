<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\GoldPriceHistory;
use Illuminate\Http\Request;
use Carbon\Carbon;

class GoldPriceController extends Controller
{
    public function index(Request $request)
    {
        $karat = $request->query('karat');
        $period = $request->query('period', 'month');

        $query = GoldPriceHistory::query();

        if ($karat) {
            $query->where('karat', $karat);
        }

        $startDate = match ($period) {
            'day' => Carbon::today(),
            'week' => Carbon::now()->subWeek(),
            'month' => Carbon::now()->subMonth(),
            'year' => Carbon::now()->subYear(),
            default => Carbon::now()->subMonth(),
        };

        $query->where('date_recorded', '>=', $startDate)
              ->orderBy('date_recorded', 'asc');

        $prices = $query->get();

        return response()->json($prices);
    }
}







