import { useContext } from "react";
import { ProductsContext } from "../context/productContext";

const useProduct = () => useContext(ProductsContext)

export default useProduct