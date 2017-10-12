import csv


def read_file():

    result = {}

    index = 0
    with open('Rat_Sightings.csv') as csvfile:
        reader = csv.reader(csvfile)

        for row in reader:
            if row[0] != 'Unique Key':

                data = {
                    'uniqueKey': row[0],
                    'createdDate': row[1],
                    'locationType': row[7],
                    'incidentZip': row[8],
                    'incidentAddress': row[9],
                    'city': row[16],
                    'borough': row[23],
                    'latitude': row[49],
                    'longitude': row[50]
                }

                result[row[0]] = data

            index += 1

    return result


def write_to_file(input_dict):

    num_key = len(input_dict)
    index = 0

    with open('rat.json', 'w') as f:
        f.writelines('{\n')
        f.writelines('    \"ratData\": {\n')

        for key in input_dict:
            f.writelines('        \"' + key + '\": {\n')
            f.writelines('            \"' + 'uniqueKey' + '\": ' + '\"' + input_dict[key]['uniqueKey'] + '\",\n')
            f.writelines('            \"' + 'createdDate' + '\": ' + '\"' + input_dict[key]['createdDate'] + '\",\n')
            f.writelines('            \"' + 'locationType' + '\": ' + '\"' + input_dict[key]['locationType'] + '\",\n')
            f.writelines('            \"' + 'incidentZip' + '\": ' + '\"' + input_dict[key]['incidentZip'] + '\",\n')
            f.writelines('            \"' + 'incidentAddress' + '\": ' + '\"' + input_dict[key]['incidentAddress'] + '\",\n')
            f.writelines('            \"' + 'city' + '\": ' + '\"' + input_dict[key]['city'] + '\",\n')
            f.writelines('            \"' + 'borough' + '\": ' + '\"' + input_dict[key]['borough'] + '\",\n')
            f.writelines('            \"' + 'latitude' + '\": ' + '\"' + input_dict[key]['latitude'] + '\",\n')
            f.writelines('            \"' + 'longitude' + '\": ' + '\"' + input_dict[key]['longitude'] + '\"\n')

            index += 1

            if index == num_key:
                f.writelines('        }\n')
            else:
                f.writelines('        },\n')

        f.writelines('    }\n')
        f.writelines('}\n')


if __name__ == '__main__':
    r = read_file()
    write_to_file(r)


