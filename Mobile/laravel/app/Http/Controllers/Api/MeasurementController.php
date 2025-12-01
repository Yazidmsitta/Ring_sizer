<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Measurement;
use Illuminate\Http\Request;

class MeasurementController extends Controller
{
    public function index(Request $request)
    {
        $measurements = $request->user()->measurements()->orderBy('created_at', 'desc')->get();

        return response()->json($measurements);
    }

    public function store(Request $request)
    {
        $request->validate([
            'name' => 'required|string|max:100',
            'type' => 'required|in:RING,FINGER,BRACELET',
            'diameter_mm' => 'nullable|numeric',
            'circumference_mm' => 'nullable|numeric',
            'size_eu' => 'nullable|numeric',
            'size_us' => 'nullable|numeric',
        ]);

        $measurement = $request->user()->measurements()->create([
            'name' => $request->name,
            'type' => $request->type,
            'diameter_mm' => $request->diameter_mm,
            'circumference_mm' => $request->circumference_mm,
            'size_eu' => $request->size_eu,
            'size_us' => $request->size_us,
            'last_synced_at' => now(),
        ]);

        return response()->json($measurement, 201);
    }

    public function update(Request $request, $id)
    {
        $measurement = $request->user()->measurements()->findOrFail($id);

        $request->validate([
            'name' => 'sometimes|string|max:100',
            'type' => 'sometimes|in:RING,FINGER,BRACELET',
            'diameter_mm' => 'nullable|numeric',
            'circumference_mm' => 'nullable|numeric',
            'size_eu' => 'nullable|numeric',
            'size_us' => 'nullable|numeric',
        ]);

        $measurement->update([
            'name' => $request->name ?? $measurement->name,
            'type' => $request->type ?? $measurement->type,
            'diameter_mm' => $request->diameter_mm ?? $measurement->diameter_mm,
            'circumference_mm' => $request->circumference_mm ?? $measurement->circumference_mm,
            'size_eu' => $request->size_eu ?? $measurement->size_eu,
            'size_us' => $request->size_us ?? $measurement->size_us,
            'last_synced_at' => now(),
        ]);

        return response()->json($measurement);
    }

    public function destroy(Request $request, $id)
    {
        $measurement = $request->user()->measurements()->findOrFail($id);
        $measurement->delete();

        return response()->json(['message' => 'Measurement deleted successfully']);
    }
}







