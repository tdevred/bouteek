import axios from "axios";
import { createAppSlice } from "../buildCreateSlice";

type ProductId = number

interface CartState {
    loading: boolean,
    productsInCart: [ProductId, number][],
}

const initialState = {
    loading: false,
    productsInCart: []
} satisfies CartState as CartState

type CartData = [ProductId, number][]

type CartPostData = {
    cart: CartData,
    user_token: string
}

const cartSlice = createAppSlice({
    name: 'cart',
    initialState,
    reducers: (create) => ({
        addToCart: create.reducer<ProductId>((state, action) => {
            console.debug("adding product to cart")

            const productId = action.payload

            const existingStack = state.productsInCart.findIndex(productTuple => productTuple[0] == productId);
            if (existingStack == -1) {
                state.productsInCart.push([productId, 1]);
            } else {
                state.productsInCart[existingStack][1] += 1;
            }
        }),
        deleteFromCart: create.reducer<ProductId>((state, action) => {
            console.debug("removing product from cart")

            const productId = action.payload

            const pileExistante = state.productsInCart.findIndex(productTuple => productTuple[0] == productId);
            if (pileExistante != -1) {
                state.productsInCart[pileExistante][1] -= 1;
            }
            state.productsInCart = state.productsInCart.filter(p => p[1] > 0)
        }),
        validateCart: create.asyncThunk(
            async ({cart, user_token}: CartPostData) => {
                const cartDataSerialized = cart.map(v => ({
                    "productId":v[0], "quantity": v[1]
                }));

                console.log({"products": cartDataSerialized})
                const res = await axios.post("http://localhost:8080/orders", {"products": cartDataSerialized}, {headers:{"Authorization": "Bearer " + user_token}})

                return res.data
            },
            {
                pending: (state) => {
                    state.loading = true
                },
                rejected: (state) => {
                    state.loading = false
                },
                fulfilled: (state) => {
                    state.loading = false
                    state.productsInCart = [];
                }
            }
        ),
    })
})

export const { addToCart, deleteFromCart, validateCart } = cartSlice.actions
export default cartSlice.reducer