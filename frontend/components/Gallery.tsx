import { useState, useEffect } from "react";
import { motion } from "framer-motion";
import { Grid, Typography, Paper, Stack, Dialog, Accordion, AccordionSummary, AccordionDetails } from "@mui/material";
import { Skeleton } from "@mui/material";
import axios from "axios";
import Image from "../interfaces/Image";


export default function Gallery() {
    const [loading, setLoading] = useState(false);
    const [value, setValue] = useState<Image>();
    const [images, setImages] = useState<Image[]>([]);

    const [open, setOpen] = useState(false);

    const toggleOpen = () => {
        setOpen(!open);
    };

    const onImageClick = (image: Image) => {
        setValue(image);
        setOpen(true);
    };

    useEffect(() => {
        setLoading(true);
        axios.get("http://localhost:3001/submissions", {
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json',
            }
        })
            .then((response) => {
                setLoading(false);
                setImages(response.data);
            })
            .catch((error) => {
                console.log(error);
            });
    }, []);

    return (
        <Paper >
            <Typography variant="h2">
                Gallery
            </Typography>



            {loading ? (
                <Grid
                    container
                    spacing={{ xs: 1, sm: 1, md: 1, lg: 1 }}
                    columns={{ xs: 12, sm: 12, md: 12, lg: 5, xl: 12 }}
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
                            <Stack direction="column" spacing={1} justifyContent="center" alignItems="center">

                                <img src={image.image} className="pic" />
                                <Typography variant="image_title">{image.title}</Typography>
                                <Typography>{image.width}x{image.width}</Typography>

                            </Stack>

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
                <img src={value?.image} style={{ height: "100%", width: "100%", objectFit: "contain" }} onClick={toggleOpen} />
            </Dialog>
        </Paper >

    );
}