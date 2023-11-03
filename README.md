# InstaLens: Real-Time Object Detection Android App

## Getting Started

### Steps to Build and Run the App
1. Clone the repository to your local machine using your preferred Git client or via command line.
2. Open the project with Android Studio (version Bumblebee or newer recommended).
3. Make sure all dependencies are synced properly with the project by checking the `build.gradle` files.
4. Connect an Android device or set up an emulator through Android Studio's AVD Manager.
5. Build the project by selecting 'Build -> Make Project' in the Android Studio menu.
6. Run the application by clicking on the 'Run -> Run 'app'' option and select the connected device or an emulator.

### Assumptions Made
- User has an Android device running on Android-Q or above
- It is assumed that the user's device has a camera that is compatible with the CameraX API.
- The user has basic understanding of how to operate an Android application.
- The device has sufficient storage to save snapshots taken during object detection.
- For generating bounding boxes, a dynamic scale-factor along x and y axes depending on view-size and image-size gave best results. Unfortunately, same failed when combining the canvas with original bitmap for saving into device.

### Challenges Faced
#### Challenge 1: Limited Support for Jetpack Compose
As Jetpack Compose is relatively new, there was limited documentation and support available during development. This was particularly challenging when trying to implement advanced camera functionalities.
As a result, the existing articles and resources did not cover the steps to implement the project at all and everything had to be developed from scratch. 
Despite all the requirements being met, inaccuracy in scaling the coordinates supplied to draw the bounding boxes from the TFLite model on the CameraX Preview screen and while saving the image are present.
However, I believe this can be addressed by diving deeper into understanding the relation between device screen size, resolution, output-image size and the camera-frame size.

**Addressed By:**
- Extensive research and experimentation were conducted to understand the nuances of Jetpack Compose.
- Utilized the latest tutorials, articles, and community forums to find solutions for specific issues related to camera integration with Compose.

#### Challenge 2: Scaling the bounding boxes over the detected objects in real-time
The RectF coordinates from the TensorFlow Lite model were not scaled to fit different device resolutions, leading to inaccuracies in drawing bounding boxes around the detected objects.

**Addressed By:**
- A robust debugging process was established to calculate the size of the camera preview screen, and the size of input and output images.
- Although significant improvements were made, a 100% accuracy in scaling across all devices could not be achieved due to the vast number of screen sizes and resolutions.

#### Challenge 3: Scaling the bounding boxes over the detected objects when saving into device
The RectF coordinates from the TensorFlow Lite model were not scaled to fit different device resolutions, leading to inaccuracies in drawing bounding boxes around the detected objects. 
However, the same scaling technique applied to address Challenge-2 did not work because the saved image was in a different resolution depending on the resolution of camera. So, a completely new logic had to be developed.

**Addressed By:**
- A robust debugging process was established to calculate the size of the camera preview screen, and the size of input and output images.
- Implemented a dynamic scaling factor that adjusts the coordinates based on the device's screen resolution.
- Unfortunately, the boxes still do not surround the detected objects when saved. However, significant improvement was seen after applying a dynamic scaling factor.


# InstaLens: Real-Time Object Detection Android App

InstaLens is an advanced object detection mobile application that leverages the power of TensorFlow Lite to identify objects in real-time through the device's camera. It's built for Android and includes several features and customizations that provide users with a seamless and interactive object detection experience.

## Screenshots

<table>
   <tr>
    <td>
      <img src="https://github.com/ranitraj/instaLens/assets/15179100/f27ec107-8d54-4dbc-88d1-c6ce73d987ef" width="400" />
    </td>
    <td>
      <img src="https://github.com/ranitraj/instaLens/assets/15179100/ee0dc990-e1e5-46d2-8ef2-a2f9fab0d23a" width="400" />
    </td>
  </tr>
  <tr>
    <td>
      <img src="https://github.com/ranitraj/instaLens/assets/15179100/1b8697f7-3875-48e0-b470-95ee73a3a132" width="400" />
    </td>
    <td>
      <img src="https://github.com/ranitraj/instaLens/assets/15179100/b713674d-ef0e-4729-b53d-8b1a6456609c" width="400" />
    </td>
  </tr>
  <tr>
    <td>
      <img src="https://github.com/ranitraj/instaLens/assets/15179100/20853da5-3440-4bfc-a440-edc1c1667fbe" width="400" />
    </td>
    <td>
      <img src="https://github.com/ranitraj/instaLens/assets/15179100/e1888079-4db2-4bfc-aa6a-050ebdaab961" width="400" />
    </td>
  </tr>
     <tr>
    <td>
      <img src="https://github.com/ranitraj/instaLens/assets/15179100/0c103440-6cd9-47a8-8c86-e4a5f1426e37" width="400" />
    </td>
    <td>
      <img src="https://github.com/ranitraj/instaLens/assets/15179100/9c037741-44b2-487b-ae7c-2c86b8bbab60" width="400" />
    </td>
  </tr>
   <tr>
    <td>
      <img src="https://github.com/ranitraj/instaLens/assets/15179100/e3169c0c-82eb-4423-b6dd-a8079607a675" width="400" />
    </td>
    <td>
      <img src="https://github.com/ranitraj/instaLens/assets/15179100/334e7970-f90a-4c0e-97b2-0177f2c0f29d" width="400" />
    </td>
  </tr>
</table>


## Features

### Object Detection Setup
InstaLens is equipped with a state-of-the-art object detection model, integrated into an Android project. It utilizes TensorFlow Lite for accurate and efficient detection of objects within the camera's viewfinder.

### Camera Rotation Support
The application supports both the front and back cameras, giving users the flexibility to switch between different perspectives for object detection. This feature enhances the app's versatility in various scenarios.

### Dynamic Detection Threshold
A user-friendly UI slider is available to adjust the confidence threshold for object detection. This allows users to filter out detections that fall below a certain confidence level. The threshold level is displayed in a percentage format next to the slider for clear visibility.

### Real-Time Detection Counter
A counter is prominently displayed at the top-right corner of the screen, showing the number of objects detected in the current frame. This feature helps users to keep track of the detection activities in real time.

### Snapshot Capture and Save
With a simple button tap, users can capture and save a snapshot of the current frame, complete with detection boxes and labels. These images can be stored in the device's gallery or a specific folder designated by the app.

### Customized Detection Categories
InstaLens adds a layer of customization by categorizing detected objects (such as people, vehicles, animals) and assigning unique colors to each category. This not only makes the detections more visually appealing but also helps in quick identification of object types.

### High Performance
The app is optimized to run smoothly, ensuring that object detection is performed in real time without any significant lag, providing users with a fluid and responsive experience.

### Dark and Light Node Support
Supports both Light and Dark mode.

## Implementation Details

The app is structured around modern Android development practices, utilizing Kotlin for its development. It features:

- A camera setup that allows for rotation, adapting to the user's choice of the front or back camera.
- An ImageAnalysis use case from the CameraX library that enables processing of the camera feed in real time.
- Integration of a TensorFlow Lite model for object detection.
- A custom `CameraFrameAnalyzer` class that handles frame analysis and object detection logic.
- Use of Kotlin coroutines for efficient background processing.
- A `DataStore` preference for storing user settings, including the confidence threshold for detections.
- UI components such as `OnBoardingPage` and `OnBoardingPageIndicator` for an intuitive user experience, introducing the app's features.
- An `ImageScalingUtils` object that ensures detected bounding boxes are scaled appropriately to the size of the display.
- Material Design components and theming for a polished UI look and feel.

With these features and its careful implementation, InstaLens stands out as a robust and user-friendly object detection app for Android users.


