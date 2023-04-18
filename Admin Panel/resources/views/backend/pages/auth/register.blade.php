<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Register | {{ config('backend.site.title') }}</title>

    <link rel="icon" type="image/x-icon" href="{{ Storage::url('assets/images/favicon/favicon.png') }}" />

    @include('backend.pages.auth.include.css')

</head>

<body class="hold-transition register-page" style="background-image: url({{ Storage::url('assets/frontend/img/arabic-pattern.jpg') }}); background-size: cover; background-repeat: no-repeat; background-position: center;">
    <div class="register-box" style="width: 50%;">
        <div class="card card-outline card-primary">
            <div class="card-header text-center">
                <a href="{{ route('home')}}" class="h3">
                    <img src="{{ Storage::url('assets/images/logo/logo.png') }}" alt="logo" height="80px">
                </a>
            </div>
            <div class="card-body">
                <p class="login-box-msg">Register a new user</p>

                <form action="{{ route('register') }}" method="post">
                    @csrf
                    <div class="row">
                        <div class="input-group mb-3" style="color: red;">
                            <x-input-error :messages="$errors->all()" class="mt-2" />
                        </div>
                        <div class="input-group mb-3 col-md-6">
                            <input type="text" class="form-control" placeholder="First Name" name="first_name"
                                value="{{ old('first_name') }}" autofocus required>
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-user"></span>
                                </div>
                            </div>
                        </div>
                        <div class="input-group mb-3 col-md-6">
                            <input type="text" class="form-control" placeholder="Last Name" name="last_name"
                                value="{{ old('last_name') }}" required>
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-user"></span>
                                </div>
                            </div>
                        </div>
                        <div class="input-group mb-3 col-md-6">
                            <input type="email" class="form-control" placeholder="Email" name="email"
                                value="{{ old('email') }}" required>
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-envelope"></span>
                                </div>
                            </div>
                        </div>
                        {{-- phone --}}
                        <div class="input-group mb-3 col-md-6">
                            <input type="text" class="form-control" placeholder="Phone Number" name="phone"
                                value="{{ old('phone') }}" required>
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-phone"></span>
                                </div>
                            </div>
                        </div>
                        {{-- date of birth --}}
                        <div class="input-group mb-3 col-md-6">
                            <input type="date" class="form-control" placeholder="Date of Birth" name="date_of_birth"
                                value="{{ old('date_of_birth') }}" required>
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-calendar"></span>
                                </div>
                            </div>
                        </div>
                        {{-- gender --}}
                        <div class="input-group mb-3 col-md-6">
                            <select class="form-control" name="gender" required>
                                <option selected disabled value="">Select Gender</option>
                                <option @if(old('gender') == "Male") selected="selected" @endif value="Male">Male</option>
                                <option @if(old('gender') == "Female") selected="selected" @endif value="Female">Female</option>
                            </select>
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-venus-mars"></span>
                                </div>
                            </div>
                        </div>
                        {{-- address --}}
                        <div class="input-group mb-3 col-md-12">
                            <textarea class="form-control" placeholder="Address" name="address"
                                 required>{{ old('address') }}</textarea>
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-map-marker"></span>
                                </div>
                            </div>
                        </div>
                        {{-- city --}}
                        <div class="input-group mb-3 col-md-6">
                            <input type="text" class="form-control" placeholder="City" name="city"
                                value="{{ old('city') }}" required>
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-city"></span>
                                </div>
                            </div>
                        </div>
                        {{-- state --}}
                        <div class="input-group mb-3 col-md-6">
                            <input type="text" class="form-control" placeholder="State" name="state"
                                value="{{ old('state') }}" required>
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-map"></span>
                                </div>
                            </div>
                        </div>
                        {{-- country --}}
                        <div class="input-group mb-3 col-md-6">
                            <select class="form-control" name="country" required>
                                <option>Select Country</option>
                                <option value="AF" @if(old('country') == "AF")selected="selected"@endif>Afghanistan</option>
                                <option value="AX" @if(old('country') == "AX")selected="selected"@endif>Aland Islands</option>
                                <option value="AL" @if(old('country') == "AL")selected="selected"@endif>Albania</option>
                                <option value="DZ" @if(old('country') == "DZ")selected="selected"@endif>Algeria</option>
                                <option value="AS" @if(old('country') == "AS")selected="selected"@endif>American Samoa</option>
                                <option value="AD" @if(old('country') == "AD")selected="selected"@endif>Andorra</option>
                                <option value="AO" @if(old('country') == "AO")selected="selected"@endif>Angola</option>
                                <option value="AI" @if(old('country') == "AI")selected="selected"@endif>Anguilla</option>
                                <option value="AQ" @if(old('country') == "AQ")selected="selected"@endif>Antarctica</option>
                                <option value="AG" @if(old('country') == "AG")selected="selected"@endif>Antigua and Barbuda</option>
                                <option value="AR" @if(old('country') == "AR")selected="selected"@endif>Argentina</option>
                                <option value="AM" @if(old('country') == "AM")selected="selected"@endif>Armenia</option>
                                <option value="AW" @if(old('country') == "AW")selected="selected"@endif>Aruba</option>
                                <option value="AU" @if(old('country') == "AU")selected="selected"@endif>Australia</option>
                                <option value="AT" @if(old('country') == "AT")selected="selected"@endif>Austria</option>
                                <option value="AZ" @if(old('country') == "AZ")selected="selected"@endif>Azerbaijan</option>
                                <option value="BS" @if(old('country') == "BS")selected="selected"@endif>Bahamas</option>
                                <option value="BH" @if(old('country') == "BH")selected="selected"@endif>Bahrain</option>
                                <option value="BD" @if(old('country') == "BD")selected="selected"@endif>Bangladesh</option>
                                <option value="BB" @if(old('country') == "BB")selected="selected"@endif>Barbados</option>
                                <option value="BY" @if(old('country') == "BY")selected="selected"@endif>Belarus</option>
                                <option value="BE" @if(old('country') == "BE")selected="selected"@endif>Belgium</option>
                                <option value="BZ" @if(old('country') == "BZ")selected="selected"@endif>Belize</option>
                                <option value="BJ" @if(old('country') == "BJ")selected="selected"@endif>Benin</option>
                                <option value="BM" @if(old('country') == "BM")selected="selected"@endif>Bermuda</option>
                                <option value="BT" @if(old('country') == "BT")selected="selected"@endif>Bhutan</option>
                                <option value="BO" @if(old('country') == "BO")selected="selected"@endif>Bolivia</option>
                                <option value="BQ" @if(old('country') == "BQ")selected="selected"@endif>Bonaire, Sint Eustatius and Saba</option>
                                <option value="BA" @if(old('country') == "BA")selected="selected"@endif>Bosnia and Herzegovina</option>
                                <option value="BW" @if(old('country') == "BW")selected="selected"@endif>Botswana</option>
                                <option value="BV" @if(old('country') == "BV")selected="selected"@endif>Bouvet Island</option>
                                <option value="BR" @if(old('country') == "BR")selected="selected"@endif>Brazil</option>
                                <option value="IO" @if(old('country') == "IO")selected="selected"@endif>British Indian Ocean Territory</option>
                                <option value="BN" @if(old('country') == "BN")selected="selected"@endif>Brunei Darussalam</option>
                                <option value="BG" @if(old('country') == "BG")selected="selected"@endif>Bulgaria</option>
                                <option value="BF" @if(old('country') == "BF")selected="selected"@endif>Burkina Faso</option>
                                <option value="BI" @if(old('country') == "BI")selected="selected"@endif>Burundi</option>
                                <option value="KH" @if(old('country') == "KH")selected="selected"@endif>Cambodia</option>
                                <option value="CM" @if(old('country') == "CM")selected="selected"@endif>Cameroon</option>
                                <option value="CA" @if(old('country') == "CA")selected="selected"@endif>Canada</option>
                                <option value="CV" @if(old('country') == "CV")selected="selected"@endif>Cape Verde</option>
                                <option value="KY" @if(old('country') == "KY")selected="selected"@endif>Cayman Islands</option>
                                <option value="CF" @if(old('country') == "CF")selected="selected"@endif>Central African Republic</option>
                                <option value="TD" @if(old('country') == "TD")selected="selected"@endif>Chad</option>
                                <option value="CL" @if(old('country') == "CL")selected="selected"@endif>Chile</option>
                                <option value="CN" @if(old('country') == "CN")selected="selected"@endif>China</option>
                                <option value="CX" @if(old('country') == "CX")selected="selected"@endif>Christmas Island</option>
                                <option value="CC" @if(old('country') == "CC")selected="selected"@endif>Cocos (Keeling) Islands</option>
                                <option value="CO" @if(old('country') == "CO")selected="selected"@endif>Colombia</option>
                                <option value="KM" @if(old('country') == "KM")selected="selected"@endif>Comoros</option>
                                <option value="CG" @if(old('country') == "CG")selected="selected"@endif>Congo</option>
                                <option value="CD" @if(old('country') == "CD")selected="selected"@endif>Congo, Democratic Republic of the Congo</option>
                                <option value="CK" @if(old('country') == "CK")selected="selected"@endif>Cook Islands</option>
                                <option value="CR" @if(old('country') == "CR")selected="selected"@endif>Costa Rica</option>
                                <option value="CI" @if(old('country') == "CI")selected="selected"@endif>Cote D'Ivoire</option>
                                <option value="HR" @if(old('country') == "HR")selected="selected"@endif>Croatia</option>
                                <option value="CU" @if(old('country') == "CU")selected="selected"@endif>Cuba</option>
                                <option value="CW" @if(old('country') == "CW")selected="selected"@endif>Curacao</option>
                                <option value="CY" @if(old('country') == "CY")selected="selected"@endif>Cyprus</option>
                                <option value="CZ" @if(old('country') == "CZ")selected="selected"@endif>Czech Republic</option>
                                <option value="DK" @if(old('country') == "DK")selected="selected"@endif>Denmark</option>
                                <option value="DJ" @if(old('country') == "DJ")selected="selected"@endif>Djibouti</option>
                                <option value="DM" @if(old('country') == "DM")selected="selected"@endif>Dominica</option>
                                <option value="DO" @if(old('country') == "DO")selected="selected"@endif>Dominican Republic</option>
                                <option value="EC" @if(old('country') == "EC")selected="selected"@endif>Ecuador</option>
                                <option value="EG" @if(old('country') == "EG")selected="selected"@endif>Egypt</option>
                                <option value="SV" @if(old('country') == "SV")selected="selected"@endif>El Salvador</option>
                                <option value="GQ" @if(old('country') == "GQ")selected="selected"@endif>Equatorial Guinea</option>
                                <option value="ER" @if(old('country') == "ER")selected="selected"@endif>Eritrea</option>
                                <option value="EE" @if(old('country') == "EE")selected="selected"@endif>Estonia</option>
                                <option value="ET" @if(old('country') == "ET")selected="selected"@endif>Ethiopia</option>
                                <option value="FK" @if(old('country') == "FK")selected="selected"@endif>Falkland Islands (Malvinas)</option>
                                <option value="FO" @if(old('country') == "FO")selected="selected"@endif>Faroe Islands</option>
                                <option value="FJ" @if(old('country') == "FJ")selected="selected"@endif>Fiji</option>
                                <option value="FI" @if(old('country') == "FI")selected="selected"@endif>Finland</option>
                                <option value="FR" @if(old('country') == "FR")selected="selected"@endif>France</option>
                                <option value="GF" @if(old('country') == "GF")selected="selected"@endif>French Guiana</option>
                                <option value="PF" @if(old('country') == "PF")selected="selected"@endif>French Polynesia</option>
                                <option value="TF" @if(old('country') == "TF")selected="selected"@endif>French Southern Territories</option>
                                <option value="GA" @if(old('country') == "GA")selected="selected"@endif>Gabon</option>
                                <option value="GM" @if(old('country') == "GM")selected="selected"@endif>Gambia</option>
                                <option value="GE" @if(old('country') == "GE")selected="selected"@endif>Georgia</option>
                                <option value="DE" @if(old('country') == "DE")selected="selected"@endif>Germany</option>
                                <option value="GH" @if(old('country') == "GH")selected="selected"@endif>Ghana</option>
                                <option value="GI" @if(old('country') == "GI")selected="selected"@endif>Gibraltar</option>
                                <option value="GR" @if(old('country') == "GR")selected="selected"@endif>Greece</option>
                                <option value="GL" @if(old('country') == "GL")selected="selected"@endif>Greenland</option>
                                <option value="GD" @if(old('country') == "GD")selected="selected"@endif>Grenada</option>
                                <option value="GP" @if(old('country') == "GP")selected="selected"@endif>Guadeloupe</option>
                                <option value="GU" @if(old('country') == "GU")selected="selected"@endif>Guam</option>
                                <option value="GT" @if(old('country') == "GT")selected="selected"@endif>Guatemala</option>
                                <option value="GG" @if(old('country') == "GG")selected="selected"@endif>Guernsey</option>
                                <option value="GN" @if(old('country') == "GN")selected="selected"@endif>Guinea</option>
                                <option value="GW" @if(old('country') == "GW")selected="selected"@endif>Guinea-Bissau</option>
                                <option value="GY" @if(old('country') == "GY")selected="selected"@endif>Guyana</option>
                                <option value="HT" @if(old('country') == "HT")selected="selected"@endif>Haiti</option>
                                <option value="HM" @if(old('country') == "HM")selected="selected"@endif>Heard Island and Mcdonald Islands</option>
                                <option value="VA" @if(old('country') == "VA")selected="selected"@endif>Holy See (Vatican City State)</option>
                                <option value="HN" @if(old('country') == "HN")selected="selected"@endif>Honduras</option>
                                <option value="HK" @if(old('country') == "HK")selected="selected"@endif>Hong Kong</option>
                                <option value="HU" @if(old('country') == "HU")selected="selected"@endif>Hungary</option>
                                <option value="IS" @if(old('country') == "IS")selected="selected"@endif>Iceland</option>
                                <option value="IN" @if(old('country') == "IN")selected="selected"@endif>India</option>
                                <option value="ID" @if(old('country') == "ID")selected="selected"@endif>Indonesia</option>
                                <option value="IR" @if(old('country') == "IR")selected="selected"@endif>Iran, Islamic Republic of</option>
                                <option value="IQ" @if(old('country') == "IQ")selected="selected"@endif>Iraq</option>
                                <option value="IE" @if(old('country') == "IE")selected="selected"@endif>Ireland</option>
                                <option value="IM" @if(old('country') == "IM")selected="selected"@endif>Isle of Man</option>
                                <option value="IL" @if(old('country') == "IL")selected="selected"@endif>Israel</option>
                                <option value="IT" @if(old('country') == "IT")selected="selected"@endif>Italy</option>
                                <option value="JM" @if(old('country') == "JM")selected="selected"@endif>Jamaica</option>
                                <option value="JP" @if(old('country') == "JP")selected="selected"@endif>Japan</option>
                                <option value="JE" @if(old('country') == "JE")selected="selected"@endif>Jersey</option>
                                <option value="JO" @if(old('country') == "JO")selected="selected"@endif>Jordan</option>
                                <option value="KZ" @if(old('country') == "KZ")selected="selected"@endif>Kazakhstan</option>
                                <option value="KE" @if(old('country') == "KE")selected="selected"@endif>Kenya</option>
                                <option value="KI" @if(old('country') == "KI")selected="selected"@endif>Kiribati</option>
                                <option value="KP" @if(old('country') == "KP")selected="selected"@endif>Korea, Democratic People's Republic of</option>
                                <option value="KR" @if(old('country') == "KR")selected="selected"@endif>Korea, Republic of</option>
                                <option value="XK" @if(old('country') == "XK")selected="selected"@endif>Kosovo</option>
                                <option value="KW" @if(old('country') == "KW")selected="selected"@endif>Kuwait</option>
                                <option value="KG" @if(old('country') == "KG")selected="selected"@endif>Kyrgyzstan</option>
                                <option value="LA" @if(old('country') == "LA")selected="selected"@endif>Lao People's Democratic Republic</option>
                                <option value="LV" @if(old('country') == "LV")selected="selected"@endif>Latvia</option>
                                <option value="LB" @if(old('country') == "LB")selected="selected"@endif>Lebanon</option>
                                <option value="LS" @if(old('country') == "LS")selected="selected"@endif>Lesotho</option>
                                <option value="LR" @if(old('country') == "LR")selected="selected"@endif>Liberia</option>
                                <option value="LY" @if(old('country') == "LY")selected="selected"@endif>Libyan Arab Jamahiriya</option>
                                <option value="LI" @if(old('country') == "LI")selected="selected"@endif>Liechtenstein</option>
                                <option value="LT" @if(old('country') == "LT")selected="selected"@endif>Lithuania</option>
                                <option value="LU" @if(old('country') == "LU")selected="selected"@endif>Luxembourg</option>
                                <option value="MO" @if(old('country') == "MO")selected="selected"@endif>Macao</option>
                                <option value="MK" @if(old('country') == "MK")selected="selected"@endif>Macedonia, the Former Yugoslav Republic of</option>
                                <option value="MG" @if(old('country') == "MG")selected="selected"@endif>Madagascar</option>
                                <option value="MW" @if(old('country') == "MW")selected="selected"@endif>Malawi</option>
                                <option value="MY" @if(old('country') == "MY")selected="selected"@endif>Malaysia</option>
                                <option value="MV" @if(old('country') == "MV")selected="selected"@endif>Maldives</option>
                                <option value="ML" @if(old('country') == "ML")selected="selected"@endif>Mali</option>
                                <option value="MT" @if(old('country') == "MT")selected="selected"@endif>Malta</option>
                                <option value="MH" @if(old('country') == "MH")selected="selected"@endif>Marshall Islands</option>
                                <option value="MQ" @if(old('country') == "MQ")selected="selected"@endif>Martinique</option>
                                <option value="MR" @if(old('country') == "MR")selected="selected"@endif>Mauritania</option>
                                <option value="MU" @if(old('country') == "MU")selected="selected"@endif>Mauritius</option>
                                <option value="YT" @if(old('country') == "YT")selected="selected"@endif>Mayotte</option>
                                <option value="MX" @if(old('country') == "MX")selected="selected"@endif>Mexico</option>
                                <option value="FM" @if(old('country') == "FM")selected="selected"@endif>Micronesia, Federated States of</option>
                                <option value="MD" @if(old('country') == "MD")selected="selected"@endif>Moldova, Republic of</option>
                                <option value="MC" @if(old('country') == "MC")selected="selected"@endif>Monaco</option>
                                <option value="MN" @if(old('country') == "MN")selected="selected"@endif>Mongolia</option>
                                <option value="ME" @if(old('country') == "ME")selected="selected"@endif>Montenegro</option>
                                <option value="MS" @if(old('country') == "MS")selected="selected"@endif>Montserrat</option>
                                <option value="MA" @if(old('country') == "MA")selected="selected"@endif>Morocco</option>
                                <option value="MZ" @if(old('country') == "MZ")selected="selected"@endif>Mozambique</option>
                                <option value="MM" @if(old('country') == "MM")selected="selected"@endif>Myanmar</option>
                                <option value="NA" @if(old('country') == "NA")selected="selected"@endif>Namibia</option>
                                <option value="NR" @if(old('country') == "NR")selected="selected"@endif>Nauru</option>
                                <option value="NP" @if(old('country') == "NP")selected="selected"@endif>Nepal</option>
                                <option value="NL" @if(old('country') == "NL")selected="selected"@endif>Netherlands</option>
                                <option value="AN" @if(old('country') == "AN")selected="selected"@endif>Netherlands Antilles</option>
                                <option value="NC" @if(old('country') == "NC")selected="selected"@endif>New Caledonia</option>
                                <option value="NZ" @if(old('country') == "NZ")selected="selected"@endif>New Zealand</option>
                                <option value="NI" @if(old('country') == "NI")selected="selected"@endif>Nicaragua</option>
                                <option value="NE" @if(old('country') == "NE")selected="selected"@endif>Niger</option>
                                <option value="NG" @if(old('country') == "NG")selected="selected"@endif>Nigeria</option>
                                <option value="NU" @if(old('country') == "NU")selected="selected"@endif>Niue</option>
                                <option value="NF" @if(old('country') == "NF")selected="selected"@endif>Norfolk Island</option>
                                <option value="MP" @if(old('country') == "MP")selected="selected"@endif>Northern Mariana Islands</option>
                                <option value="NO" @if(old('country') == "NO")selected="selected"@endif>Norway</option>
                                <option value="OM" @if(old('country') == "OM")selected="selected"@endif>Oman</option>
                                <option value="PK" @if(old('country') == "PK")selected="selected"@endif>Pakistan</option>
                                <option value="PW" @if(old('country') == "PW")selected="selected"@endif>Palau</option>
                                <option value="PS" @if(old('country') == "PS")selected="selected"@endif>Palestinian Territory, Occupied</option>
                                <option value="PA" @if(old('country') == "PA")selected="selected"@endif>Panama</option>
                                <option value="PG" @if(old('country') == "PG")selected="selected"@endif>Papua New Guinea</option>
                                <option value="PY" @if(old('country') == "PY")selected="selected"@endif>Paraguay</option>
                                <option value="PE" @if(old('country') == "PE")selected="selected"@endif>Peru</option>
                                <option value="PH" @if(old('country') == "PH")selected="selected"@endif>Philippines</option>
                                <option value="PN" @if(old('country') == "PN")selected="selected"@endif>Pitcairn</option>
                                <option value="PL" @if(old('country') == "PL")selected="selected"@endif>Poland</option>
                                <option value="PT" @if(old('country') == "PT")selected="selected"@endif>Portugal</option>
                                <option value="PR" @if(old('country') == "PR")selected="selected"@endif>Puerto Rico</option>
                                <option value="QA" @if(old('country') == "QA")selected="selected"@endif>Qatar</option>
                                <option value="RE" @if(old('country') == "RE")selected="selected"@endif>Reunion</option>
                                <option value="RO" @if(old('country') == "RO")selected="selected"@endif>Romania</option>
                                <option value="RU" @if(old('country') == "RU")selected="selected"@endif>Russian Federation</option>
                                <option value="RW" @if(old('country') == "RW")selected="selected"@endif>Rwanda</option>
                                <option value="BL" @if(old('country') == "BL")selected="selected"@endif>Saint Barthelemy</option>
                                <option value="SH" @if(old('country') == "SH")selected="selected"@endif>Saint Helena</option>
                                <option value="KN" @if(old('country') == "KN")selected="selected"@endif>Saint Kitts and Nevis</option>
                                <option value="LC" @if(old('country') == "LC")selected="selected"@endif>Saint Lucia</option>
                                <option value="MF" @if(old('country') == "MF")selected="selected"@endif>Saint Martin</option>
                                <option value="PM" @if(old('country') == "PM")selected="selected"@endif>Saint Pierre and Miquelon</option>
                                <option value="VC" @if(old('country') == "VC")selected="selected"@endif>Saint Vincent and the Grenadines</option>
                                <option value="WS" @if(old('country') == "WS")selected="selected"@endif>Samoa</option>
                                <option value="SM" @if(old('country') == "SM")selected="selected"@endif>San Marino</option>
                                <option value="ST" @if(old('country') == "ST")selected="selected"@endif>Sao Tome and Principe</option>
                                <option value="SA" @if(old('country') == "SA")selected="selected"@endif>Saudi Arabia</option>
                                <option value="SN" @if(old('country') == "SN")selected="selected"@endif>Senegal</option>
                                <option value="RS" @if(old('country') == "RS")selected="selected"@endif>Serbia</option>
                                <option value="CS" @if(old('country') == "CS")selected="selected"@endif>Serbia and Montenegro</option>
                                <option value="SC" @if(old('country') == "SC")selected="selected"@endif>Seychelles</option>
                                <option value="SL" @if(old('country') == "SL")selected="selected"@endif>Sierra Leone</option>
                                <option value="SG" @if(old('country') == "SG")selected="selected"@endif>Singapore</option>
                                <option value="SX" @if(old('country') == "SX")selected="selected"@endif>Sint Maarten</option>
                                <option value="SK" @if(old('country') == "SK")selected="selected"@endif>Slovakia</option>
                                <option value="SI" @if(old('country') == "SI")selected="selected"@endif>Slovenia</option>
                                <option value="SB" @if(old('country') == "SB")selected="selected"@endif>Solomon Islands</option>
                                <option value="SO" @if(old('country') == "SO")selected="selected"@endif>Somalia</option>
                                <option value="ZA" @if(old('country') == "ZA")selected="selected"@endif>South Africa</option>
                                <option value="GS" @if(old('country') == "GS")selected="selected"@endif>South Georgia and the South Sandwich Islands</option>
                                <option value="SS" @if(old('country') == "SS")selected="selected"@endif>South Sudan</option>
                                <option value="ES" @if(old('country') == "ES")selected="selected"@endif>Spain</option>
                                <option value="LK" @if(old('country') == "LK")selected="selected"@endif>Sri Lanka</option>
                                <option value="SD" @if(old('country') == "SD")selected="selected"@endif>Sudan</option>
                                <option value="SR" @if(old('country') == "SR")selected="selected"@endif>Suriname</option>
                                <option value="SJ" @if(old('country') == "SJ")selected="selected"@endif>Svalbard and Jan Mayen</option>
                                <option value="SZ" @if(old('country') == "SZ")selected="selected"@endif>Swaziland</option>
                                <option value="SE" @if(old('country') == "SE")selected="selected"@endif>Sweden</option>
                                <option value="CH" @if(old('country') == "CH")selected="selected"@endif>Switzerland</option>
                                <option value="SY" @if(old('country') == "SY")selected="selected"@endif>Syrian Arab Republic</option>
                                <option value="TW" @if(old('country') == "TW")selected="selected"@endif>Taiwan, Province of China</option>
                                <option value="TJ" @if(old('country') == "TJ")selected="selected"@endif>Tajikistan</option>
                                <option value="TZ" @if(old('country') == "TZ")selected="selected"@endif>Tanzania, United Republic of</option>
                                <option value="TH" @if(old('country') == "TH")selected="selected"@endif>Thailand</option>
                                <option value="TL" @if(old('country') == "TL")selected="selected"@endif>Timor-Leste</option>
                                <option value="TG" @if(old('country') == "TG")selected="selected"@endif>Togo</option>
                                <option value="TK" @if(old('country') == "TK")selected="selected"@endif>Tokelau</option>
                                <option value="TO" @if(old('country') == "TO")selected="selected"@endif>Tonga</option>
                                <option value="TT" @if(old('country') == "TT")selected="selected"@endif>Trinidad and Tobago</option>
                                <option value="TN" @if(old('country') == "TN")selected="selected"@endif>Tunisia</option>
                                <option value="TR" @if(old('country') == "TR")selected="selected"@endif>Turkey</option>
                                <option value="TM" @if(old('country') == "TM")selected="selected"@endif>Turkmenistan</option>
                                <option value="TC" @if(old('country') == "TC")selected="selected"@endif>Turks and Caicos Islands</option>
                                <option value="TV" @if(old('country') == "TV")selected="selected"@endif>Tuvalu</option>
                                <option value="UG" @if(old('country') == "UG")selected="selected"@endif>Uganda</option>
                                <option value="UA" @if(old('country') == "UA")selected="selected"@endif>Ukraine</option>
                                <option value="AE" @if(old('country') == "AE")selected="selected"@endif>United Arab Emirates</option>
                                <option value="GB" @if(old('country') == "GB")selected="selected"@endif>United Kingdom</option>
                                <option value="US" @if(old('country') == "US")selected="selected"@endif>United States</option>
                                <option value="UM" @if(old('country') == "UM")selected="selected"@endif>United States Minor Outlying Islands</option>
                                <option value="UY" @if(old('country') == "UY")selected="selected"@endif>Uruguay</option>
                                <option value="UZ" @if(old('country') == "UZ")selected="selected"@endif>Uzbekistan</option>
                                <option value="VU" @if(old('country') == "VU")selected="selected"@endif>Vanuatu</option>
                                <option value="VE" @if(old('country') == "VE")selected="selected"@endif>Venezuela</option>
                                <option value="VN" @if(old('country') == "VN")selected="selected"@endif>Viet Nam</option>
                                <option value="VG" @if(old('country') == "VG")selected="selected"@endif>Virgin Islands, British</option>
                                <option value="VI" @if(old('country') == "VI")selected="selected"@endif>Virgin Islands, U.s.</option>
                                <option value="WF" @if(old('country') == "WF")selected="selected"@endif>Wallis and Futuna</option>
                                <option value="EH" @if(old('country') == "EH")selected="selected"@endif>Western Sahara</option>
                                <option value="YE" @if(old('country') == "YE")selected="selected"@endif>Yemen</option>
                                <option value="ZM" @if(old('country') == "ZM")selected="selected"@endif>Zambia</option>
                                <option value="ZW" @if(old('country') == "ZW")selected="selected"@endif>Zimbabwe</option>
                            </select>
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-flag"></span>
                                </div>
                            </div>
                        </div>
                        {{-- zip code --}}
                        <div class="input-group mb-3 col-md-6">
                            <input type="text" class="form-control" placeholder="Zip Code" name="zip_code" value="{{ old('zip_code') }}" required>
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-map-marker-alt"></span>
                                </div>
                            </div>
                        </div>
                        {{-- password --}}
                        <div class="input-group mb-3 col-md-6">
                            <input type="password" class="form-control" placeholder="Password" name="password" required>
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-lock"></span>
                                </div>
                            </div>
                        </div>
                        <div class="input-group mb-3 col-md-6">
                            <input type="password" class="form-control" placeholder="Retype password" name="password_confirmation" required>
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-lock"></span>
                                </div>
                            </div>
                        </div>
                        <div class="row mb-3 col-md-12">
                            <div class="col-6">
                                <div class="icheck-primary">
                                    <input type="checkbox" id="agreeTerms" name="terms" value="agree">
                                    <label for="agreeTerms">
                                        I agree to the <a href="#">terms</a>
                                    </label>
                                </div>
                            </div>
                            <!-- /.col -->
                            <div class="col-6">
                                <button type="submit" class="btn btn-primary btn-block">Register</button>
                            </div>
                            <!-- /.col -->
                        </div>
                    </div>
                </form>

                <a href="{{ route('login') }}" class="text-center">I already have an account</a>
            </div>
            <!-- /.form-box -->
        </div><!-- /.card -->
    </div>
    <!-- /.login-box -->

    @include('backend.pages.auth.include.js')

</body>

</html>
