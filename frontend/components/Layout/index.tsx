// components/Layout.js
import { Grid } from '@mui/material';
import BottomAppBar from './Mobile/BottomAppBar';
import LeftPanel from './Panels/LeftPanel';
const Layout = ({ children }) => {
    return (
        <Grid container>
            <Grid
                item
                md={4}
                lg={3}
                xl={2}
                sx={{
                    display: { xs: "none", sm: "none", md: "block", lg: "block" },
                }}
            >
                <LeftPanel />
            </Grid>
            <Grid item xs={12} sm={12} md={8} lg={9} xl={10}>
                {children}
            </Grid>

            <Grid
                item
                xs={12}
                sx={{
                    display: { xs: "block", lg: "none" },
                }}
            >
                <BottomAppBar />
            </Grid>
        </Grid>
    );
};

export default Layout;