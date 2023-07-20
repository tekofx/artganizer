import { useState } from "react";
import { motion } from "framer-motion";
import { Grid, Typography, Paper, Stack, Dialog, Accordion, AccordionSummary, AccordionDetails } from "@mui/material";
import { Skeleton } from "@mui/material";
import Theme from "../src/theme";
import LaunchIcon from '@mui/icons-material/Launch';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';

interface Image {
    src: string;
    author: string;
    url: string;
}

const images = [

    {
        src: "https://d.furaffinity.net/art/teko./1688318035/1688318035.teko._summer.png",
        author: "Liss",
        url: "https://d.furaffinity.net/art/teko./1688318035/1688318035.teko._summer.png"
    },
    {
        src: "https://d.furaffinity.net/art/teko./1659823270/1659823270.teko._autumn_-_no_logo__large_.jpg",
        author: "Teko",
        url: "https://d.furaffinity.net/art/teko./1659823270/1659823270.teko._autumn_-_no_logo__large_.jpg"
    }, {
        src:  "https://d.furaffinity.net/art/teko./1603045397/1603045397.teko._halloween_-_solo.jpg",
        author: "Teko",
        url: "https://d.furaffinity.net/art/teko./1603045397/1603045397.teko._halloween_-_solo.jpg"
    },
    {
        src: "https://d.furaffinity.net/art/teko./1599652020/1599652020.teko._camara.jpg",
        author: "Teko",
        url: "https://d.furaffinity.net/art/teko./1599652020/1599652020.teko._camara.jpg"
    },
    {
        src: "https://d.furaffinity.net/art/teko./1588506092/1588506092.teko._ref.jpg",
        author: "Teko",
        url: "https://d.furaffinity.net/art/teko./1588506092/1588506092.teko._ref.jpg"
    }


]

export default function Gallery() {
    const [loading, setLoading] = useState(false);
    const [value, setValue] = useState<Image>(images[0]);
    const [expanded, setExpanded] = useState(true);

    const [open, setOpen] = useState(false);

    const toggleOpen = () => {
        setOpen(!open);
    };

    const onImageClick = (image:Image) => {
        setValue(image);
        setOpen(true);
    };

    return (
        <Paper >
            <Typography variant="h2">
                Recent submissions
            </Typography>
            
         

                {loading ? (
                    <Grid
                        container
                        spacing={{ xs: 1, sm: 1, md: 1, lg: 1 }}
                        columns={{ xs: 12, sm: 12, md: 12, lg: 12, xl: 12 }}
                    >
                        {Array.from(Array(30).keys()).map((_, i) => (
                            <Grid item lg={2} key={i}>
                                <Skeleton variant="rectangular" sx={{ minHeight: 200 }} />
                            </Grid>
                        ))}
                    </Grid>
                ) : (
                    <div className="gallery">

                        {images.map((image) => (
                            <motion.div className="pics"
                                whileHover={{ scale: 1.05 }}
                                transition={{ type: "spring", stiffness: 400, damping: 20 }}
                                onClick={() => onImageClick(image)}
                            >
                                <img src={image.src} className="pic" />
                               
                            </motion.div>
                        ))}
                    </div>

                )}

            <Dialog open={open} onClose={toggleOpen} maxWidth="lg" PaperProps={{
                style: {
                    backgroundColor: 'transparent',
                    backgroundImage: "unset",
                    boxShadow: 'none',
                },
            }}>
                <img src={value.src}  style={{ height: "100%", width: "100%", objectFit: "contain" }} onClick={toggleOpen} />
            </Dialog>
        </Paper >

    );
}