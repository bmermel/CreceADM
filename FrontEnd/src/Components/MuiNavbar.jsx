import React from 'react'
import { AppBar, Toolbar, Typography, Stack, Button, Icon } from '@mui/material'
import {navfondo} from '../styles/NavBar.module.css'
import ApartmentIcon from '@mui/icons-material/Apartment';
import LoginIcon from '@mui/icons-material/Login';

const MuiNavbar = () => {
  return (
    <AppBar className={navfondo}>
        <Toolbar>
        <Icon size='large' edge='start' color='inherit' aria-label='logo' sx={{paddingRight: "10px"}}>
        <ApartmentIcon />
        </Icon>
        <Typography fontSize="medium" variant="h6"component='span' sx={{flexGrow: 1}}>
            Crece Buenos Aires
        </Typography>
        <Stack direction='row'>
        
        <Button color='inherit' fontSize="medium" startIcon={<LoginIcon/>}>Ingresar</Button>
        </Stack>
        </Toolbar>
    </AppBar>
  )
}

export default MuiNavbar