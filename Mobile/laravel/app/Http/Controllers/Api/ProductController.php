<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Product;
use Illuminate\Http\Request;

class ProductController extends Controller
{
    public function index(Request $request)
    {
        $query = Product::with('vendor')->where('is_available', true);

        if ($request->has('category')) {
            $query->where('category', $request->category);
        }

        if ($request->has('karat')) {
            $query->where('karat', $request->karat);
        }

        if ($request->has('vendor_id')) {
            $query->where('vendor_id', $request->vendor_id);
        }

        if ($request->has('min_price')) {
            $query->where('price', '>=', $request->min_price);
        }

        if ($request->has('max_price')) {
            $query->where('price', '<=', $request->max_price);
        }

        $products = $query->get();

        return response()->json($products);
    }

    public function show($id)
    {
        $product = Product::with(['vendor', 'images'])->findOrFail($id);

        return response()->json($product);
    }
}







