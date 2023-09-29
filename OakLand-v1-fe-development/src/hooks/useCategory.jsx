import { useContext } from "react";
import { CategoryContext } from "../context/categoryContext";

const useCategory = () => useContext(CategoryContext)

export default useCategory