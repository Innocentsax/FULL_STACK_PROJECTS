import { useContext } from "react"
import { PersonContext } from "../context/personContext"


const usePerson = () => useContext(PersonContext)

export default usePerson