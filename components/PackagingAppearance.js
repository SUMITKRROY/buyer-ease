import React, { useState } from 'react';
import { View, Image, TouchableOpacity, Text, StyleSheet } from 'react-native';
import ImageViewer from './ImageViewer'; // Import your existing image viewer component

const PackagingAppearance = ({ images }) => {
  const [selectedImage, setSelectedImage] = useState(null);
  const [isFullScreen, setIsFullScreen] = useState(false);

  const handleImagePress = (image) => {
    setSelectedImage(image);
    setIsFullScreen(true);
  };

  return (
    <View style={styles.container}>
      {/* Image count display */}
      <Text style={styles.imageCountText}>{images.length} Images</Text>
      
      {/* Image gallery */}
      <View style={styles.imageGallery}>
        {images.map((image, index) => (
          <TouchableOpacity 
            key={index} 
            onPress={() => handleImagePress(image)}
            style={styles.imageContainer}
          >
            <Image source={{ uri: image.uri }} style={styles.thumbnailImage} />
          </TouchableOpacity>
        ))}
      </View>

      {/* Reusing your existing image viewer component */}
      {isFullScreen && (
        <ImageViewer
          image={selectedImage}
          visible={isFullScreen}
          onClose={() => setIsFullScreen(false)}
        />
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    padding: 10,
  },
  imageCountText: {
    fontSize: 16,
    fontWeight: 'bold',
    marginBottom: 10,
  },
  imageGallery: {
    flexDirection: 'row',
    flexWrap: 'wrap',
  },
  imageContainer: {
    width: '30%', // Adjust as needed
    margin: '1.66%',
    aspectRatio: 1,
  },
  thumbnailImage: {
    width: '100%',
    height: '100%',
    borderRadius: 5,
  },
  modalContainer: {
    flex: 1,
    backgroundColor: 'rgba(0, 0, 0, 0.9)',
    justifyContent: 'center',
    alignItems: 'center',
  },
  fullScreenImage: {
    width: '100%',
    height: '80%',
  },
  closeButton: {
    position: 'absolute',
    top: 40,
    right: 20,
    zIndex: 1,
    backgroundColor: 'rgba(255, 255, 255, 0.3)',
    borderRadius: 15,
    width: 30,
    height: 30,
    justifyContent: 'center',
    alignItems: 'center',
  },
  closeButtonText: {
    color: 'white',
    fontSize: 18,
    fontWeight: 'bold',
  },
});

export default PackagingAppearance; 