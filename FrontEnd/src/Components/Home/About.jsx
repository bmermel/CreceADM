import React from 'react'
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import {fuente, italic, card, buttonStyle} from '../../styles/About.module.css'

const About = () => {
  return (
    <Stack className={card}>
      <h1 className={fuente}>Bienvenido</h1>
      <p className={italic}><strong><em> Crece Buenos Aires</em></strong> es una organización que tiene en claro sus objetivos, proyectando una nueva generación de Administración de Consorcios, gobernando y aprovechando los recursos, hacia el cumplimiento de sus objetivos, y así mejorar la calidad de vida de su Consorcio.</p>
      <h4>¿Sos propietario/inquilino? Iniciá sesión.</h4>
      <Button variant="contained" className = {buttonStyle} sx={{borderRadius: '16px'}}>Iniciar sesion</Button>
    </Stack>
  )
}

export default About