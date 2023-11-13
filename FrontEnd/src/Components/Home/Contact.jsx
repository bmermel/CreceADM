import React from 'react'
import Stack from '@mui/material/Stack';
import IconButton from '@mui/material/IconButton';
import FacebookIcon from '@mui/icons-material/Facebook';


const Contact = () => {
  return (
    <Stack sx={{textAlign:"center"}} gap={3} justifyContent="space-around" alignItems="center" flexDirection="row" flexWrap="wrap">
    
    <span>Direccion: Iberá 5383 CABA 1431 Buenos Aires, Argentina</span>
    <span>Teléfonos: 15-3070-1191 / 1189 </span>
    <span>Email: admcrece@gmail.com</span>
    <span>
      Redes Sociales: 
    <IconButton color='inherit'>
      <FacebookIcon/>
      </IconButton>

    </span>

    </Stack>
  )
}

export default Contact