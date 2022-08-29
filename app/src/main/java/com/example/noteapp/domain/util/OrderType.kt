package com.example.noteapp.domain.util

sealed class OrderType{
    object Ascending: OrderType()
    object Descending: OrderType()
}
