import { Product } from "../../../components/types/products";
import { createAppSlice } from "../buildCreateSlice";
import axios from "axios";

interface ProductsState {
    loading: boolean,
    products: Product[],
}

const initialState = {
    loading: true,
    products: [{id: 1, name: "eau", category: "Eaux", description: "de l'eau fraiche", price: 6}]
} satisfies ProductsState as ProductsState

const productSlice = createAppSlice({
    name: 'products',
    initialState,
    reducers: (create) => ({
        fetchProducts: create.asyncThunk(
            async () => {
                const res = await axios.get("http://localhost:8080/products")
                return res.data as Product[]
            },
            {
                pending: (state) => {
                    state.loading = true
                },
                rejected: (state) => {
                    state.loading = false
                },
                fulfilled: (state, action) => {
                    state.loading = false
                    state.products = action.payload;
                }
            }
        ),
    })
})

export const { fetchProducts } = productSlice.actions
export default productSlice.reducer