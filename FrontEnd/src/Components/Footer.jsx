import React from 'react'
import Stack from '@mui/material/Stack';
import Contact from "../Components/Home/Contact"
import {fondo} from "../styles/Contact.module.css"

const Footer = () => {
    return (
      <Stack className={fondo}>
        <Contact/>
      </Stack>
    )
  }
  
  export default Footer