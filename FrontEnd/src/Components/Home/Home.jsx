import React from 'react'
import Stack from '@mui/material/Stack';
import About from './About';
import { hero } from "../../styles/Hero.module.css"

const Home = () => {
  return (
    <Stack direction="column" flexWrap="wrap" justifyContent="space-around" alignItems="center" sx={{height: "90vh", width: "100vw"}} className={hero}>
      <About />
    </Stack>
  )
}

export default Home