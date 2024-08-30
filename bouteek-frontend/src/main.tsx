import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import 'bootstrap/dist/css/bootstrap.css';
import './index.css'
import "bootstrap-icons/font/bootstrap-icons.css";
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import LayoutRouter from './components/reduxed/LayoutRouter';
import ProductListRouter from './components/reduxed/ProductListRouter';
import ClientPageRouter from './components/reduxed/ClientPageRouter';
import LoginRouter from './components/reduxed/LoginRouter';
import { configureStore, Tuple } from '@reduxjs/toolkit';
import { thunk } from 'redux-thunk';
import { Provider, TypedUseSelectorHook, useDispatch, useSelector } from 'react-redux';
import { useStore } from 'react-redux';

import userSlice, { connect } from './store/reducers/user/slice'
import productsSlice, { fetchProducts } from './store/reducers/products/slice'
import cartSlice, { addToCart } from './store/reducers/cart/slice'

const store = configureStore({
  reducer: {
    user: userSlice,
    products: productsSlice,
    cart: cartSlice
  }, middleware: () => new Tuple(thunk)
})

//const store = configureStore({ reducer: {client: clientReducer, products: productReducer, cart: cartReducer}, middleware: () => new Tuple(thunk) })

export type AppStore = typeof store

export type RootState = ReturnType<AppStore['getState']>

export type AppDispatch = AppStore['dispatch']

export const useAppDispatch: () => AppDispatch = useDispatch
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector
export const useAppStore: () => AppStore = useStore

console.log("started with router")

store.dispatch(fetchProducts())
store.dispatch(connect({email:"toto@gmail.com", password:"totototi"}))
store.dispatch(addToCart(1))
store.dispatch(addToCart(1))
store.dispatch(addToCart(1))
store.dispatch(addToCart(1))
store.dispatch(addToCart(1))

const router = createBrowserRouter([
  {
    path: "/",
    element: <Provider store={store}><LayoutRouter /></Provider>,
    children: [
      {
        path: "produits",
        element: <ProductListRouter pageSize={5} />
      }, {
        path: "login",
        element: <LoginRouter />
      }, {
        path: "panier",
        element: <ClientPageRouter />
      },
    ]
  }
])

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>,
)