import {Stack} from '@mui/material'
import React from 'react'
import {fuente, italic, card} from '../../styles/About.module.css'


const UserLogin = () => {
  return (
    <Stack className={card}>
    <h1 className={fuente}>Contacto</h1>
    <Stack sx={{flexDirection: "row", alignContent:"left" }}>
      <div>
        <p>Crece Buenos Aires</p>
        <p>Iberá 5383 CABA</p>
        <p>1431 Buenos Aires, Argentina</p>
      </div>
      <div>
        <p>Teléfonos: 15-3070-1191 / 1189</p>
        <p>Email: admcrece@gmail.com</p>
        </div>
    </Stack>  
    </Stack>

  )
}

export default UserLogin