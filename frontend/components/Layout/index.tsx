// components/Layout.js
import { Grid } from '@mui/material';
import { useAppContext } from 'pages/_app';
import BottomAppBar from './Mobile/BottomAppBar';
import LeftPanel from './Panels/LeftPanel';
export default function Layout({ children }) {
    const { isMobile } = useAppContext();
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

            {
                isMobile && (
                    <Grid
                        item
                        xs={12}
                    >
                        <BottomAppBar />
                    </Grid>
                )
            }
        </Grid>
    );
};
