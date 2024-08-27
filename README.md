<h1>AnimatedPB</h1>

  <h2>Description</h2>
  <p>
    AnimatedPB is an Android library for creating customizable and animated progress bars. It supports various shapes like polls and circles, allows for animation customization (linear, ease-in, ease-out, bounce), and offers extensive styling options for progress bars and containers. With easy-to-use attributes, you can set colors, labels, and more to create engaging UI elements.
  </p>
<h2>Demo</h2>

![Librarygif-ezgif com-video-to-gif-converter](https://github.com/user-attachments/assets/c96a645b-507b-449b-9546-9533edf01c67)


  <h2><img src="https://github.com/YosiBs/Pokemon-Escape-Mobile-Game/assets/105666011/008a508e-5484-46ba-be36-ac359d603f01" alt=pic5 width="40" height="40"> Features</h2>
  <ul>
    <li>Customizable colors and shapes</li>
    <li>Supports circular and poll progress bars</li>
    <li>Animated progress with different types (linear, ease-in, ease-out, bounce)</li>
    <li>Flexible margins, corner radii, and dimensions</li>
    <li>Display text labels and values inside progress bars</li>
  </ul>

  <h2><img src="https://github.com/user-attachments/assets/4980fb42-e8b7-447c-86e9-007d8fb72644" alt=pic5 width="40" height="40"> Installation</h2>
  <p>To use AnimatedPB in your project, follow these steps:</p>
  <pre>
    <!-- In your project-level build.gradle -->
    repositories {
      mavenCentral()
    }

    <!-- In your app-level build.gradle -->
    dependencies {
      implementation 'com.example:animatedpb:1.0.0'
    }
  </pre>

  <h2><img src="https://github.com/YosiBs/Gotcha-App/assets/105666011/0c7e3507-e910-4ac4-b5e3-8c5d484fa682" alt=pic5 width="40" height="40"> Usage</h2>
  
### Here is a sample usage of `AnimatedPB`:
  
```xml
   <com.example.animatedpb.Models.AnimatedPB
        android:id="@+id/my_animatedPB"
        android:layout_width="match_parent"
        android:layout_height="40dp"
        app:progressColor="#4CAF50"
        app:containerColor="#F5F5F5"
        app:barLabel="Linear"
        app:barShape="poll"
        app:minValue="0"
        app:maxValue="100"
        app:progress="0"
        app:animationType="linear"
        app:valueToDisplay="number"
        app:containerCornerRadius="0"
        app:progressCornerRadius="0"
        app:labelColor="#212121"
        app:valueColor="#212121"
        />
```

  <h2><img src="https://github.com/user-attachments/assets/7908e7a2-ff92-4cb0-9101-96fc8a4efd30" alt=pic5 width="40" height="40"> Attributes</h2>
  <p>Here’s a table of all customizable attributes for AnimatedPB:</p>

<table border="1" cellpadding="10" cellspacing="0">
  <thead>
    <tr>
      <th>Attribute</th>
      <th>Description</th>
      <th>Example</th>
      <th>Default Value</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><code>progressColor</code></td>
      <td>Sets the color of the progress bar</td>
      <td><code>#4CAF50</code></td>
      <td><code>#005ECB</code></td>
    </tr>
    <tr>
      <td><code>containerColor</code></td>
      <td>Sets the background color of the progress bar's container</td>
      <td><code>#F5F5F5</code></td>
      <td><code>#E1BEE7</code></td>
    </tr>
    <tr>
      <td><code>barLabel</code></td>
      <td>Text label to display within the progress bar</td>
      <td><code>Linear</code></td>
      <td><code>null</code></td>
    </tr>
    <tr>
      <td><code>barShape</code></td>
      <td>Determines the shape of the progress bar. Options: <code>poll</code> (0), <code>circle</code> (1)</td>
      <td><code>poll</code></td>
      <td><code>0 (poll)</code></td>
    </tr>
    <tr>
      <td><code>minValue</code></td>
      <td>Sets the minimum value for the progress bar</td>
      <td><code>0</code></td>
      <td><code>0</code></td>
    </tr>
    <tr>
      <td><code>maxValue</code></td>
      <td>Sets the maximum value for the progress bar</td>
      <td><code>100</code></td>
      <td><code>100</code></td>
    </tr>
    <tr>
      <td><code>progress</code></td>
      <td>Initial progress value</td>
      <td><code>50</code></td>
      <td><code>0</code></td>
    </tr>
    <tr>
      <td><code>animationType</code></td>
      <td>Sets the animation type for progress. Options: <code>linear</code> (0), <code>ease_out</code> (1), <code>ease_in</code> (2), <code>bounce</code> (3)</td>
      <td><code>linear</code></td>
      <td><code>0 (linear)</code></td>
    </tr>
    <tr>
      <td><code>valueToDisplay</code></td>
      <td>Determines how to display the progress value. Options: <code>number</code> (0), <code>percentage</code> (1), <code>none</code> (2)</td>
      <td><code>number</code></td>
      <td><code>0 (number)</code></td>
    </tr>
    <tr>
      <td><code>containerCornerRadius</code></td>
      <td>Radius of the container's corners</td>
      <td><code>20</code></td>
      <td><code>0</code></td>
    </tr>
    <tr>
      <td><code>progressCornerRadius</code></td>
      <td>Radius of the progress bar's corners</td>
      <td><code>10</code></td>
      <td><code>0</code></td>
    </tr>
    <tr>
      <td><code>labelColor</code></td>
      <td>Color of the progress bar's label text</td>
      <td><code>#FFFFFF</code></td>
      <td><code>#212121</code></td>
    </tr>
    <tr>
      <td><code>labelTextSize</code></td>
      <td>Size of the label text</td>
      <td><code>16</code></td>
      <td><code>14</code></td>
    </tr>
    <tr>
      <td><code>valueColor</code></td>
      <td>Color of the displayed value inside the progress bar</td>
      <td><code>#FFFFFF</code></td>
      <td><code>#212121</code></td>
    </tr>
    <tr>
      <td><code>progressMarginTop</code></td>
      <td>Margin of the progress bar from the top</td>
      <td><code>10dp</code></td>
      <td><code>0</code></td>
    </tr>
    <tr>
      <td><code>progressMarginLeft</code></td>
      <td>Margin of the progress bar from the left</td>
      <td><code>18dp</code></td>
      <td><code>0</code></td>
    </tr>
    <tr>
      <td><code>progressMarginBottom</code></td>
      <td>Margin of the progress bar from the bottom</td>
      <td><code>10dp</code></td>
      <td><code>0</code></td>
    </tr>
    <tr>
      <td><code>progressMarginRight</code></td>
      <td>Margin of the progress bar from the right</td>
      <td><code>18dp</code></td>
      <td><code>0</code></td>
    </tr>
    <tr>
      <td><code>animDurationMillis</code></td>
      <td>Duration of the animation in milliseconds</td>
      <td><code>1000</code></td>
      <td><code>1000</code></td>
    </tr>
  </tbody>
</table>


 <h2><img src="https://github.com/YosiBs/Gotcha-App/assets/105666011/9f5d6637-b1e1-4037-8f60-64388e5ab109" alt=pic5 width="40" height="40"> Authors</h2>
<ul>
    <li><a href="https://github.com/YosiBs">Yosi Ben Shushan</a></li>
</ul>
