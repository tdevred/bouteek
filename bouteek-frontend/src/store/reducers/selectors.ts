import { createSelector } from "reselect";
import { Product } from "../../components/types/products";
import { RootState } from "../../main";

const getCart = (state: RootState) => state.cart.productsInCart;
const getProducts = (state: RootState) => state.products.products;

export const getProductsInCart = createSelector([getCart, getProducts], (cart, products) => {
    const result = cart.map(
        ([productId, quantity]) => ([
            products.find(pstore => pstore.id == productId),
            quantity
        ] as [Product | undefined, number]))

    return result.filter(p => p[0] != undefined) as [Product, number][]
})

export const getProductCountInCart = createSelector([getCart], (cart) => {
    return cart.map(p => p[1]).reduce((a, b) => a+b, 0)
})