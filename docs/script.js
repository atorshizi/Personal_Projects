// Intersection Observer for fade-in animations
const observerOptions = {
    threshold: 0.1,
    rootMargin: '0px 0px -60px 0px'
};

const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            entry.target.classList.add('visible');
        }
    });
}, observerOptions);

// Observe all fade-in elements
document.querySelectorAll('.fade-in').forEach(el => observer.observe(el));

// Navbar scroll effect
let lastScroll = 0;
const navbar = document.querySelector('.navbar');

window.addEventListener('scroll', () => {
    const currentScroll = window.pageYOffset;
    
    if (currentScroll > 100) {
        navbar.style.padding = '0.75rem 0';
        navbar.style.boxShadow = '0 4px 30px rgba(0, 0, 0, 0.3)';
    } else {
        navbar.style.padding = '1rem 0';
        navbar.style.boxShadow = 'none';
    }
    
    lastScroll = currentScroll;
}, { passive: true });

// Modal functionality with enhanced data
const modals = {
    tockos: {
        title: 'Embedded TockOS on ENTS Board',
        content: `
            <p>Implemented SDI-12 communication protocol for the Tock Operating System in Rust. This including kernel drivers, USART logic, and custom syscalls intended for environmental sensor measurement in remote locations.</p>
            <p>The ENTS (Environmental NeTworked Sensing) board is a custom low-power embedded platform developed for long-term soil monitoring. It features an STM32 microcontroller, ESP32 microcontroller for WiFi communication, LoRaWAN connectivity, and various interfaces.</p>
            <div class="modal-images">
                <img src="Screenshots/Tock/ents.png" alt="ENTS Board">
                <img src="Screenshots/Tock/tock.png" alt="TockOS Architecture">
            </div>
            <p>Developed user-space applications in C to test kernel implementations and to run continuous sensor measurements, with data transmitted wirelessly over LoRaWAN.</p>
            <p>Built an Arduino sensor simulator to test both valid and malformed inputs. This was used to compare the TockOS implementation against existing bare-metal C firmware for reliability and power consumption. The results showed a 31% increase in power consumption with the operating systems but eliminated all reliability issues that caused the bare metal implementation to hang.</p>
            <p>Given that these board are intended for deployment in remote locations with limited access to maintenance, reliability is a much more critical factor and thus, this signaled a significant improvement in system robustness and uptime.</p>
        `,
        link: 'https://github.com/jlab-sensing/tock/tree/ents-sdi12-transmit'
    },
    cloud: {
        title: 'CloudImage',
        content: `
            <p>The goal of this project was to allow users to use either their phones or website to upload and store images to the cloud which saves space on their personal devices and to view their images on any device anywhere they are.</p>
            <p>In this full-stack project, I developed a back-end server hosted on AWS EC2 using Spring Boot Framework with REST API principles. The back-end was connected to a PostgreSQL database which stored user information, and uploaded images were stored on AWS S3. User authentication was built in using JWT.</p>
            <div class="modal-images">
                <img src="Screenshots/CloudImage/Screenshot 2024-09-11 151323.png" alt="CloudImage">
                <img src="Screenshots/CloudImage/Screenshot_20240911_150227_CloudImage.png" alt="CloudImage">
                <img src="Screenshots/CloudImage/Screenshot_20240911_150230_CloudImage.png" alt="CloudImage">
                <img src="Screenshots/CloudImage/Screenshot_20240911_150319_CloudImage.png" alt="CloudImage">
            </div>
            <p>An Android app was created to allow easy mobile usage of the service. Users can create accounts, log in, and upload images either by taking photos directly from the app or choosing existing images from their gallery.</p>
            <div class="modal-images">
                <img src="Screenshots/CloudImage/Screenshot 2024-09-11 150841.png" alt="CloudImage">
                <img src="Screenshots/CloudImage/Screenshot 2024-09-11 150927.png" alt="CloudImage">
                <img src="Screenshots/CloudImage/Screenshot 2024-09-11 150939.png" alt="CloudImage">
                <img src="Screenshots/CloudImage/Screenshot 2024-09-11 150950.png" alt="CloudImage">
            </div>
            <p>A React website was created for users to view and upload images with a focus on simple and responsive web development.</p>
        `,
        link: 'https://github.com/atorshizi/Personal_Projects/tree/main/CloudImage'
    },
    tarot: {
        title: 'Tarot Card Reader',
        content: `
            <p>This project is a web app that generates responses to various questions using Tarot Card Readings. Users can create an account and generate daily fortunes based on generated cards, which are saved to their account and accessible on any device.</p>
            <p>Daily fortunes are stored on a Firebase database and generated using VertexAI's API. The entire project is hosted using Firebase hosting, with user information stored on a Firebase database.</p>
            <div class="modal-images">
                <img src="Screenshots/Tarot Card Reader/ex1.png" alt="Tarot">
                <img src="Screenshots/Tarot Card Reader/ex2.png" alt="Tarot">
                <img src="Screenshots/Tarot Card Reader/ex3.png" alt="Tarot">
                <img src="Screenshots/Tarot Card Reader/ex4.png" alt="Tarot">
            </div>
        `,
        link: 'https://github.com/atorshizi/Personal_Projects/tree/main/Tarot%20Card%20Reader/source'
    },
    pantry: {
        title: 'PantryPal',
        content: `
            <p>This desktop app generates and stores recipes for food. Recipes are generated by recording the user saying the type of meal they would like (Breakfast, Lunch, Dinner, etc.) and the ingredients they have available to use for the meal.</p>
            <p>Voice recordings are transcribed using the Whisper API, then ChatGPT's API suggests a meal with instructions. The DALL-E API generates a sample image of what the meal could look like. Generated recipes are stored on MongoDB and can be viewed, edited, or deleted across devices.</p>
            <div class="modal-images">
                <img src="Screenshots/login.png" alt="PantryPal">
                <img src="Screenshots/create.png" alt="PantryPal">
                <img src="Screenshots/home.png" alt="PantryPal">
            </div>
            <p>The user can log in with their credentials or create a new account. Once logged in, they can access previously saved recipes or add new ones. The "remember me" feature allows bypassing login on future visits.</p>
            <div class="modal-images">
                <img src="Screenshots/rec.png" alt="PantryPal">
                <img src="Screenshots/recing.png" alt="PantryPal">
                <img src="Screenshots/gen.png" alt="PantryPal">
            </div>
            <p>Users record the type of meal and ingredients, then receive a confirmation page where they can manually edit or have ChatGPT and DALL-E generate a recipe and image.</p>
            <div class="modal-images">
                <img src="Screenshots/filt.png" alt="PantryPal">
                <img src="Screenshots/det.png" alt="PantryPal">
            </div>
            <p>Users can access previously saved recipes with sorting (by name or time) and filtering (by meal type) options. Clicking a recipe shows detailed ingredients, instructions, and images.</p>
        `,
        link: 'https://github.com/atorshizi/Personal_Projects/tree/79628b268bd6d09c629dfae177248366346acb40/PantryPal/src'
    },
    python: {
        title: 'Python IDLE Multi-tab Support',
        content: `
            <p>Added multi-tab support to Python's built-in IDE (Python IDLE). In the current production version, each file opens in a new window, making navigation between files very difficult.</p>
            <p>This modification to the open-source codebase adds a navigation bar allowing easy switching between opened or created files with a close button for each, significantly improving the development workflow.</p>
            <div class="modal-images">
                <img src="Screenshots/IDLE Codebase Modification/before.png" alt="IDLE">
            </div>
            <p>Above shows the current version of IDLE when multiple files are opened - each in a separate window.</p>
            <div class="modal-images">
                <img src="Screenshots/IDLE Codebase Modification/1.png" alt="IDLE">
                <img src="Screenshots/IDLE Codebase Modification/3.png" alt="IDLE">
            </div>
            <p>The modifications enable switching between files and closing any opened files with ease.</p>
        `,
        link: 'https://github.com/atorshizi/Personal_Projects/tree/main/Multi-tab%20support%20for%20IDLE'
    },
    ocr: {
        title: 'OCR Android App',
        content: `
            <p>Android app that recognizes text in images from existing photos or new camera captures - a form of Optical Character Recognition (OCR). It can also scan one or multiple barcodes/QR codes from a single picture, displaying raw data and format type across 13 different barcode/QR code formats.</p>
            <p>The app incorporates Google ML Kit SDK for all recognition functionality.</p>
            <div class="modal-images">
                <img src="Screenshots/Screenshot_20220909-014106_OCR.png" alt="OCR">
                <img src="Screenshots/Screenshot_20220909-014226_Camera.png" alt="OCR">
                <img src="Screenshots/Screenshot_20220909-014237_OCR.png" alt="OCR">
                <img src="Screenshots/Screenshot_20220909-014800_Camera.png" alt="OCR">
                <img src="Screenshots/Screenshot_20220909-014652_OCR.png" alt="OCR">
                <img src="Screenshots/Screenshot_20220909-014540_OCR.png" alt="OCR">
            </div>
            <p>Above shows the startup page, a demo where the user takes a picture of a sentence which is then processed and displayed as text, and a demo of barcode/QR code scanning with value extraction.</p>
        `,
        link: 'https://github.com/atorshizi/Personal_Projects/tree/main/OCR/app/src'
    },
    dcgan: {
        title: 'DCGAN Emoji Generator',
        content: `
            <p>Re-implemented the Deep Convolutional Generative Adversarial Network (DCGAN) architecture in PyTorch and applied it to a dataset of emoji images collected from Apple, Google, Samsung, and other platforms.</p>
            <p>Conducted a latent space interpolation experiment that showed smooth transitions between generated images, confirming the model learned meaningful representations rather than memorizing training data.</p>
            <div class="modal-images">
                <img src="Screenshots/dcgan/mode.png" alt="Latent Space Walk">
            </div>
            <p>Training on the full dataset led to mode collapse, so a curated face-emoji subset was created alongside modified training strategies: a 2:1 generator-to-discriminator update ratio, perceptual loss via pretrained VGG16, and feature matching loss. These changes stabilized training, eliminated collapse, and produced recognizable emoji faces with coherent features.</p>
            <div class="modal-images">
                <img src="Screenshots/dcgan/latent.png" alt="Training Convergence">
                <img src="Screenshots/dcgan/epoch.png" alt="Loss Curves">
            </div>
            <p>Above are examples of latent space interpolation and generated images at different training epochs after modifications</p>
        `,
        link: 'https://github.com/atorshizi/Personal_Projects/tree/main/DCGAN'
    },
    asic: {
        title: 'Custom ASIC Encoder & Decoder',
        content: `
            <p>Designed a custom application-specific integrated circuit (ASIC) for a Viterbi encoder and decoder on a 9-bit system. The instruction set architecture (ISA) was designed with a total of 8 instructions taking up 3 bits, with remaining bits depending on instruction type.</p>
            <div class="modal-images-tall">
                <img src="../Custom ASIC/isa.png" alt="ASIC">
                <img src="../Custom ASIC/op1.png" alt="ASIC">
                <img src="../Custom ASIC/op2.png" alt="ASIC">
            </div>
            <p>A custom assembler was created in Python that translated assembly code written in the custom ISA to machine code loaded into instruction ROM. Machine modules were coded in SystemVerilog, simulated in ModelSim, and synthesized in Quartus.</p>
            <div class="modal-images-tall">
                <img src="../Custom ASIC/overview.png" alt="ASIC">
                <img src="../Custom ASIC/alu.png" alt="ASIC">
                <img src="../Custom ASIC/decoder-example.png" alt="ASIC">
            </div>
            <p>Above shows an overview of the machine architecture, the ALU module design, and a brief section of the decoder written in the custom ISA.</p>
        `,
        link: 'https://github.com/atorshizi/Personal_Projects/tree/main/Custom%20ASIC'
    },
    mac: {
        title: '2D Systolic Array-Based AI Accelerator Design',
        content: `
            <p>
                Implemented a 2D systolic array–based AI accelerator targeting the
                multiply–accumulate (MAC) operations used in convolutional neural networks (CNNs).
                The baseline design streams activations and weights through a grid of processing
                elements, enabling spatial data reuse and high throughput.
            </p>

            <p>
                The accelerator was functionally verified using activations and weights derived
                from a CNN-based software model. Simulation inputs and expected outputs were
                provided via structured files, allowing cycle-accurate verification of partial
                sums and final outputs against software ground truth.
            </p>

            <p>
                To improve efficiency, sparsity was introduced through pruning to create phases 
                where portions of the systolic array were not actively contributing to computation. 
                Clock gating was then implemented to exploit this sparsity by disabling unused MAC 
                processing elements during inactive cycles.
            </p>

            <p>
                The design was mapped to FPGA both before and after clock gating to quantify its
                impact. Power and timing were measured at the slow process corner
                using. While performance remained effectively unchanged (~127 MHz), clock gating 
                reduced core dynamic power usage by 20% and improved energy efficiency.
            </p>

            <div class="modal-images">
                <img src="Screenshots/npu/arch.png" alt="architecture overview">
                <img src="Screenshots/npu/clock.png" alt="clock gating overview">
            </div>

            <p>
                Additional architectural modifications were layered on top of the baseline array,
                including tiling, array parameterization, and configurable activation functions. 
            </p>

        `,
        hideLink: true,
        link: '...'
    },
    ttt: {
        title: 'TicTacToe Android App',
        content: `
            <p>Android app of the classic TicTacToe game that shows which player's turn it is and can determine when a winner or tie has been achieved.</p>
            <div class="modal-images">
                <img src="Screenshots/Screenshot_20220908-104452_TicTacToe.png" alt="TicTacToe">
                <img src="Screenshots/Screenshot_20220908-104507_TicTacToe.png" alt="TicTacToe">
                <img src="Screenshots/Screenshot_20220908-104524_TicTacToe.png" alt="TicTacToe">
                <img src="Screenshots/Screenshot_20220908-104536_TicTacToe.png" alt="TicTacToe">
                <img src="Screenshots/Screenshot_20220908-104611_TicTacToe.png" alt="TicTacToe">
            </div>
            <p>The images above demonstrate the game at startup and different scenarios where player X wins, player O wins, or the game ends in a tie.</p>
        `,
        link: 'https://github.com/atorshizi/Personal_Projects/tree/main/TicTacToe/app/src'
    },
    face: {
        title: 'Facial Recognition',
        content: `
            <p>Created a face recognition pipeline to match pictures to people. Interest points were extracted using both uniform and edge sampling with OpenCV's Harris corner detector. Patches around interest points were extracted using regular patch feature extraction and SIFT feature extraction.</p>
            <p>A "visual vocabulary" was created using K-means clustering with scikit-learn's KMeans model fitted on training data features. The cluster centers were used to obtain feature vectors for each image. A K-nearest neighbor classifier was used with k=3 and k=5. Feature dimension was 128 with 50 clusters for k-means.</p>
            <div class="modal-images">
                <img src="../Face Recognition/1.png" alt="Face Recognition">
                <img src="../Face Recognition/results.png" alt="Face Recognition">
            </div>
            <p>The best accuracy score was achieved with k=5, uniform sampling, and SIFT feature detection, reaching 94% accuracy.</p>
        `,
        link: 'https://github.com/atorshizi/Personal_Projects/tree/main/Face%20Recognition'
    },
    spam: {
        title: 'Email Spam Filter',
        content: `
            <p>Machine learning algorithm using TensorFlow to classify emails as spam or not spam. Uses a neural network with two hidden layers and bag-of-words vectorization.</p>
            <p>The dataset is split with roughly 80% for training and 20% for testing, achieved in TrainModel.py and TestModel.py respectively. Testing achieved over 96% accuracy.</p>
            <div class="modal-images">
                <img src="Screenshots/CL1.png" alt="Spam Filter">
                <img src="Screenshots/CL2.png" alt="Spam Filter">
                <img src="Screenshots/CL3.png" alt="Spam Filter">
            </div>
            <p>Shown are example loss vs epoch graphs during training, accuracy achieved on the test dataset, and a screenshot of the training process.</p>
        `,
        link: 'https://github.com/atorshizi/Personal_Projects/tree/main/Classification'
    }
};

function openModal(projectId) {
    const modal = modals[projectId];
    if (!modal) return;

    const modalContent = `
        <div class="modal-header">
            <h2 class="modal-title">${modal.title}</h2>
            <button class="modal-close" onclick="closeModal()" aria-label="Close modal">&times;</button>
        </div>
        <div class="modal-body">
            ${modal.content}
        </div>
        <div class="modal-footer">
            ${modal.hideLink ? '' : `<a href="${modal.link}" target="_blank" rel="noopener noreferrer" class="btn btn-link">View Source Code</a>`}
            <button class="btn btn-secondary" onclick="closeModal()">Close</button>
        </div>
    `;

    const modalContentEl = document.getElementById('modalContent');
    const modalOverlay = document.getElementById('modalOverlay');
    
    if (modalContentEl && modalOverlay) {
        modalContentEl.innerHTML = modalContent;
        modalOverlay.classList.add('active');
        document.body.style.overflow = 'hidden';
        
        // Focus trap for accessibility
        modalContentEl.querySelector('.modal-close').focus();
    }
}

function closeModal() {
    const modalOverlay = document.getElementById('modalOverlay');
    if (modalOverlay) {
        modalOverlay.classList.remove('active');
        document.body.style.overflow = '';
    }
}

// Close modal when clicking outside content
const modalOverlay = document.getElementById('modalOverlay');
if (modalOverlay) {
    modalOverlay.addEventListener('click', (e) => {
        if (e.target.id === 'modalOverlay') {
            closeModal();
        }
    });
}

// Keyboard accessibility
document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape') {
        closeModal();
    }
});

// Smooth reveal for hero elements on page load
document.addEventListener('DOMContentLoaded', () => {
    // Add loaded class for any initial animations
    document.body.classList.add('loaded');
});
