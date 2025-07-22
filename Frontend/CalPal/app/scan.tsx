import React, { useEffect, useState } from "react";
import { Text, View, StyleSheet, Button } from "react-native";
import { CameraView, Camera } from "expo-camera";

interface NutritionData {
  calories: number;
  fat: number;
  carbs: number;
  protein: number;
}

export default function ScanScreen() {
  const [hasPermission, setHasPermission] = useState<boolean | null>(null);
  const [scanned, setScanned] = useState(false);
  const [data, setData] = useState<NutritionData | null>(null);

  useEffect(() => {
    (async () => {
      const { status } = await Camera.requestCameraPermissionsAsync();
      setHasPermission(status === "granted");
    })();
  }, []);

  const handleBarcodeScanned = async ({ type, data: scannedData }: { type: string; data: string }) => {
    setScanned(true);

    try {
      // Replace localhost with your actual backend IP if testing on device
      const response = await fetch(`http://192.168.1.214:8080/api/nutritionix/lookup?upc=${scannedData}`);
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const json: NutritionData = await response.json();
      setData(json);
    } catch (error) {
      console.error("Scan failed:", error);
      setData(null);
    }
  };

  if (hasPermission === null) return <Text>Requesting camera permission...</Text>;
  if (hasPermission === false) return <Text>No access to camera</Text>;

  return (
    <View style={styles.container}>
      <CameraView
        onBarcodeScanned={scanned ? undefined : handleBarcodeScanned}
        barcodeScannerSettings={{ barcodeTypes: ["ean13", "upc_e", "upc_a"] }} // include UPC formats
        style={StyleSheet.absoluteFillObject}
      />

      {scanned && (
        <Button
          title="Tap to Scan Again"
          onPress={() => {
            setScanned(false);
            setData(null);
          }}
        />
      )}

      {data && (
        <View style={styles.infoContainer}>
          <Text>Calories: {data.calories}</Text>
          <Text>Fat: {data.fat}</Text>
          <Text>Carbs: {data.carbs}</Text>
          <Text>Protein: {data.protein}</Text>
        </View>
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, flexDirection: "column", justifyContent: "center" },
  infoContainer: { padding: 20, backgroundColor: "#fff", marginTop: 20 },
});
